import { test, expect } from '@playwright/test';
import { LoginAPI } from '../../pages/API/login-api.js';
import { CreateAccountAPI } from '../../pages/API/createaccount-api.js';
import { CustomersAPI } from '../../pages/API/customers-api.js';
import { DatabaseAPI } from '../../pages/API/database-api.js';
import { AccountType } from '../../test-data/api/accountType.data.js';
import { getAccountID, getCustomerID } from '../../test-data/api/customer.data.js';

test.describe('Kiểm tra Create Account API', () => {
    let loginAPI;
    let customerAPI;
    let createAccountAPI;
    let databaseAPI;

    test.beforeEach(async ({ request }) => {
        loginAPI = new LoginAPI(request);
        customerAPI = new CustomersAPI(request);
        createAccountAPI = new CreateAccountAPI(request);

        databaseAPI = new DatabaseAPI(request);
        const res = await databaseAPI.initializeDB();
        expect(res.ok()).toBeTruthy();
    });

    for (let data of AccountType()) {
        test(`CreateAcc-TC01: Tạo tài khoảng theo ${data.case} `, async () => {

            const customerID = await getCustomerID(loginAPI);
            const fromAccountID = await getAccountID(loginAPI, customerAPI);

            const createAccountRes = await createAccountAPI.createAccount(customerID, data.type, fromAccountID);
            expect(createAccountRes.status()).toBe(200);
        });
    }
});