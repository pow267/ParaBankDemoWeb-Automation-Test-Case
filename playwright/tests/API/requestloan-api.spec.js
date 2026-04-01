import { test, expect } from '@playwright/test';
import { LoginAPI } from '../../pages/API/login-api.js';
import { CustomersAPI } from '../../pages/API/customers-api.js';
import { RequestLoanAPI } from '../../pages/API/requestLoan-api.js';
import { getAccountID, getCustomerID } from '../../test-data/api/customer.data.js';
import { DatabaseAPI } from '../../pages/API/database-api.js';

test.describe('Kiểm tra Request Loan API', () => {
    let loginAPI;
    let customersAPI;
    let requestLoanAPI;
    let customerID;
    let accountID;
    let databaseAPI;

    test.beforeEach(async ({ request }) => {
        loginAPI = new LoginAPI(request);
        customersAPI = new CustomersAPI(request);
        requestLoanAPI = new RequestLoanAPI(request);

        databaseAPI = new DatabaseAPI(request);
        const res = await databaseAPI.initializeDB();
        expect(res.ok()).toBeTruthy();

        customerID = await getCustomerID(loginAPI);
        accountID = await getAccountID(loginAPI, customersAPI);
    });

    test('RequestLoan-TC1: Request Loan thành công', async () => {
        const res = await requestLoanAPI.requestLoan(customerID, 1000, 100, accountID);
        expect(res.status()).toBe(200);

        const data = await res.json();
        expect(data.approved).toBe(true);
    });

    test('RequestLoan-TC2: Request Loan bị từ chối do số tiền quá lớn', async () => {

        const res = await requestLoanAPI.requestLoan(customerID, 999999, 10, accountID);
        expect(res.status()).toBe(200);

        const body = await res.json();
        expect(body.approved).toBe(false);
        expect(body).toHaveProperty('message');
        expect(body.message).toContain('error.insufficient.funds');
    });
});