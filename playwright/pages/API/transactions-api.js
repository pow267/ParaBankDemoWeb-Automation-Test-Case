export class TransactionsAPI {
    constructor(request) {
        this.request = request
    }

    async getTransactions(accountId) {
        const res = await this.request.get(`accounts/${accountId}/transactions`, {
            headers: { 'Accept': 'application/json' }
        })
        return res
    }

    async getTransactionsByDate(accountId, fromDate, toDate) {
        const res = await this.request.get(`accounts/${accountId}/transactions/fromDate/${fromDate}/toDate/${toDate}`, {
            headers: { 'Accept': 'application/json' }
        })
        return res
    }
}