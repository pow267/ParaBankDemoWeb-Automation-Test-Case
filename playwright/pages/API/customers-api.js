export class CustomersAPI {
    constructor(request) {
        this.request = request;
    }

    async getCustomer(id) {
        const res = await this.request.get(`customers/${id}`, {
            headers: { 'Accept': 'application/json' }
        });
        return res;
    }

    async getAccounts(id) {
        const res = await this.request.get(`customers/${id}/accounts`, {
            headers: { 'Accept': 'application/json' }
        });
        return res;
    }
}
