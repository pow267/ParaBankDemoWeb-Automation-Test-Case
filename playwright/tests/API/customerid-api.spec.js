import { test, expect } from "@playwright/test";
import { CustomersAPI } from '../../pages/API/customers-api.js';
import { LoginAPI } from '../../pages/API/login-api.js';
import { DatabaseAPI } from "../../pages/API/database-api.js";
import { getCustomerID } from "../../test-data/api/customer.data.js";

test.describe('Kiểm tra API CustomerID', () => {
    let customersAPI;
    let loginAPI;
    let databaseAPI;

    test.beforeEach(async ({ request }) => {
        customersAPI = new CustomersAPI(request);
        loginAPI = new LoginAPI(request);

        databaseAPI = new DatabaseAPI(request);
        const res = await databaseAPI.initializeDB();
        expect(res.ok()).toBeTruthy();
    });

    test('CustomerID-TC1: API trả về 200 cho ID hợp lệ', async () => {

        const id = await getCustomerID(loginAPI);
        const res = await customersAPI.getCustomer(id);
        expect(res.status()).toBe(200);
    });

    test('CustomerID-TC2: API trả về 400 cho ID không tồn tại', async () => {
        const res = await customersAPI.getCustomer('999999');
        expect(res.status()).toBe(400);
    });
});
