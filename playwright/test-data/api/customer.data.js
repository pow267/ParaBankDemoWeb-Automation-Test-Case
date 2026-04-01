import { expect } from '@playwright/test';

export const loginData = {
    username: 'john',
    password: 'demo'
};

export async function login(loginAPI) {
    const loginRes = await loginAPI.login(loginData.username, loginData.password);
    expect(loginRes.status()).toBe(200);

    const customer = await loginRes.json();
    return customer;
}

export async function getCustomerID(loginAPI) {
    const customer = await login(loginAPI);
    return customer.id;
}

export async function getAccounts(loginAPI, customersAPI) {
    const customerID = await getCustomerID(loginAPI);

    const accountRes = await customersAPI.getAccounts(customerID);
    expect(accountRes.status()).toBe(200);

    const accounts = await accountRes.json();
    expect(accounts.length).toBeGreaterThanOrEqual(1);

    return accounts;
}

export async function getAccountID(loginAPI, customersAPI, index = 0) {
    const accounts = await getAccounts(loginAPI, customersAPI);
    return accounts[index].id;
}

