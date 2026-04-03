export class RegisterPage {
    constructor(page) {
        this.page = page;

        this.registerLink = this.page.getByRole('link', { name: 'Register' });
        this.registerButton = this.page.getByRole('button', { name: 'Register' });
        this.firstNameInput = this.page.locator('[id="customer.firstName"]');
        this.lastNameInput = this.page.locator('[id="customer.lastName"]');
        this.addressInput = this.page.locator('[id="customer.address.street"]');
        this.cityInput = this.page.locator('[id="customer.address.city"]');
        this.stateInput = this.page.locator('[id="customer.address.state"]');
        this.zipCodeInput = this.page.locator('[id="customer.address.zipCode"]');
        this.phoneInput = this.page.locator('[id="customer.phoneNumber"]');
        this.ssnInput = this.page.locator('[id="customer.ssn"]');
        this.usernameInput = this.page.locator('[id="customer.username"]');
        this.passwordInput = this.page.locator('[id="customer.password"]')
        this.confirmInput = this.page.locator('#repeatedPassword');
        this.errorMessage = this.page.locator('span.error');
        this.successMessage = this.page.getByRole('heading', { name: /Welcome/i });
    }

    async goto() {
        await this.page.goto('http://localhost:3000/parabank/index.htm');
    }

    async registerLinkClick() {
        await Promise.all([
            this.page.waitForLoadState('load'),
            this.registerLink.click()
        ]);
    }

    async fillRegisterForm(data) {
        if (data.firstName !== undefined) await this.firstNameInput.fill(data.firstName);
        if (data.lastName !== undefined) await this.lastNameInput.fill(data.lastName);
        if (data.address !== undefined) await this.addressInput.fill(data.address);
        if (data.city !== undefined) await this.cityInput.fill(data.city);
        if (data.state !== undefined) await this.stateInput.fill(data.state);
        if (data.zipCode !== undefined) await this.zipCodeInput.fill(data.zipCode);
        if (data.phone !== undefined) await this.phoneInput.fill(data.phone);
        if (data.ssn !== undefined) await this.ssnInput.fill(data.ssn);
        if (data.username !== undefined) await this.usernameInput.fill(data.username);
        if (data.password !== undefined) await this.passwordInput.fill(data.password);
        if (data.confirmPassword !== undefined) await this.confirmInput.fill(data.confirmPassword);
    }

    async register(data) {
        await this.fillRegisterForm(data);
        await Promise.all([
            this.page.waitForLoadState('load'),
            this.registerButton.click()
        ]);
    }
}