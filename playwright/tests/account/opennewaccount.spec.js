import { test, expect } from '../../fixture/login-fixture.js';
import { OpenNewAccount } from '../../pages/account/OpenNewAccountPage.js';
import { DatabaseAPI } from '../../pages/API/database-api.js';
import { AccountType, fromAccountId } from '../../test-data/account/opennewaccount.data.js';

test.describe('Kiểm tra chức năng Open New Account', () => {

    for (let type of AccountType()) {
        test(`NewAccount-TC1: Thực hiện mở mới tài khoản type ${type.case}`, async ({ page, request, login }) => {
            const openNewAccount = new OpenNewAccount(page);
            const databaseAPI = new DatabaseAPI(request);
            const cleanRes = await databaseAPI.cleanDB();
            await expect(cleanRes.ok()).toBeTruthy();
            const seedRes = await databaseAPI.initializeDB();
            await expect(seedRes.ok()).toBeTruthy();

            await openNewAccount.openNewAccount(type.TYPE, fromAccountId());
            await expect(openNewAccount.successMessage).toBeVisible();

        });
    }

});

