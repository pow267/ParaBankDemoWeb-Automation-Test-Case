export class LoginAPI {
    constructor(request) {
        this.request = request;
    }

    async login(username, password) {
        const res = await this.request.get(`login/${username}/${password}`, {
            headers: { 'Accept': 'application/json' }
        });
        return res;
    }
}
