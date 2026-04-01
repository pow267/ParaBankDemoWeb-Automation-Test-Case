export class CreateAccountAPI {
    constructor(request) {
        this.request = request;
    }

    async createAccount(customerID, newAccountType, fromAccountID) {
        const res = await this.request.post(`createAccount?customerId=${customerID}&newAccountType=${newAccountType}&fromAccountId=${fromAccountID}`, {
            headers: { 'accept': 'application/json' }
        })
        return res;
    }
}