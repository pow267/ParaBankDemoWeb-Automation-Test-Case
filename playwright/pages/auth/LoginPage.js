export class LoginPage {
    constructor(page) {
        this.page = page;

        this.usernameInput = this.page.locator('input[name="username"]');
        this.passwordInput = this.page.locator('input[name="password"]');
        this.loginButton = this.page.getByRole('button', { name: 'Log In' });
        this.successMessage = this.page.getByText('Welcome');
        this.logoutButton = this.page.getByRole('link', { name: 'Log Out' });
        this.errorMessage = this.page.locator('.error');
    }

    async goto() {
        await this.page.goto('http://localhost:3000/parabank');
    }

    async login(username, password) {
        await this.usernameInput.fill(username);
        await this.passwordInput.fill(password);
        await this.loginButton.click();
    }

    async logout() {
        await this.logoutButton.click();
    }
}