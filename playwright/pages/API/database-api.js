export class DatabaseAPI {
    constructor(request) {
        this.request = request;
    }

    async initializeDB() {
        const response = await this.request.post(`initializeDB`, {
            headers: { 'Accept': 'application/json' }
        });
        return response;
    }

    async cleanDB() {
        const response = await this.request.post(`cleanDB`, {
            headers: { 'Accept': 'application/json' }
        });
        return response;
    }
}
