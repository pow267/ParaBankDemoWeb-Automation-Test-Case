import { test, expect } from '@playwright/test';
import { LoginAPI } from '../../pages/API/login-api.js';
import { DatabaseAPI } from '../../pages/API/database-api.js';
import { CustomersAPI } from '../../pages/API/customers-api.js';
import { AccountsAPI } from '../../pages/API/accounts-api.js';
import { getAccountID } from '../../test-data/api/customer.data.js';

test.describe('Kiểm tra Account Detail API', () => {
    let loginAPI;
    let databaseAPI;
    let customerAPI;
    let accountsAPI;

    test.beforeEach(async ({ request }) => {
        loginAPI = new LoginAPI(request);
        customerAPI = new CustomersAPI(request);
        accountsAPI = new AccountsAPI(request);

        databaseAPI = new DatabaseAPI(request);
        const res = await databaseAPI.initializeDB();
        expect(res.ok()).toBeTruthy();
    });

    test('AccountDetail-TC1: thực hiện xem Detail của Account', async () => {
        const accountID = await getAccountID(loginAPI, customerAPI, 1);

        const accountDetails = await accountsAPI.getAccountDetails(accountID);
        expect(accountDetails.status()).toBe(200);
    });
});
