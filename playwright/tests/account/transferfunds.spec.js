import { test, expect } from '../../fixture/login-fixture.js';
import { TransferFundsPage } from '../../pages/account/TransferFundsPage.js';
import { fromAccountId, toAccountId } from '../../test-data/account/opennewaccount.data.js';
import { OpenNewAccount } from '../../pages/account/OpenNewAccountPage.js';
import { DatabaseAPI } from '../../pages/API/database-api.js';

test.describe('Kiểm tra chức năng Transfer Funds', () => {
    let transferFundsPage;
    let openNewAccount;
    let databaseAPI;


    test.beforeEach(async ({ page, request, login }) => {
        transferFundsPage = new TransferFundsPage(page);
        openNewAccount = new OpenNewAccount(page);
        databaseAPI = new DatabaseAPI(request);
        const cleanRes = await databaseAPI.cleanDB();
        await expect(cleanRes.ok()).toBeTruthy();
        const seedRes = await databaseAPI.initializeDB();
        await expect(seedRes.ok()).toBeTruthy();

    })
    test('TransferFunds-TC1: Thực hiện chuyển tiền từ tài khoản này sang tài khoản mới được tạo', async () => {

        await transferFundsPage.transferFunds(1000, fromAccountId(), toAccountId());
        await expect(transferFundsPage.successMessage).toBeVisible();
    });

    test('TransferFunds-TC2: Thực hiện chuyển tiền số âm', async () => {

        await transferFundsPage.transferFunds(-1000, fromAccountId(), toAccountId());
        await expect(transferFundsPage.errorMessage).toContainText('Error!');
    });

    test('TransferFunds-TC3: Thực hiện chuyển tiền số 0', async () => {

        await transferFundsPage.transferFunds(0, fromAccountId(), toAccountId());
        await expect(transferFundsPage.errorMessage).toContainText('Error!');
    });

    test('TransferFunds-TC4: Thực hiện chuyển tiền số cực lớn', async () => {

        await transferFundsPage.transferFunds(9999999999999999999, fromAccountId(), toAccountId());
        await expect(transferFundsPage.errorMessage).toBeVisible();
    });

    test('TransferFunds-TC5: Bỏ trống số tiền cần chuyển', async ({ page }) => {

        await transferFundsPage.transferFunds('', fromAccountId(), toAccountId());
        await expect(page.locator('#amount-empty-error')).toHaveText('The amount cannot be empty.');
    });
});