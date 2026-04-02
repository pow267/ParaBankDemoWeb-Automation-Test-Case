export class OpenNewAccount {
    constructor(page) {
        this.page = page;

        this.openNewAccountLink = this.page.getByRole('link', { name: 'Open New Account' });
        this.selectType = this.page.locator('#type');
        this.fromAccountID = this.page.locator('#fromAccountId');
        this.openNewAccountButton = this.page.getByRole('button', { name: 'Open New Account' });
        this.successMessage = this.page.getByText('Congratulations, your account');
        this.newAccount = this.page.locator('#newAccountId');
    }

    async openNewAccount(type, fromAccountId) {
        await Promise.all([
            this.page.waitForLoadState('load'),
            this.openNewAccountLink.click()
        ])

        await this.selectType.selectOption(type);
        await this.fromAccountID.selectOption(fromAccountId);
        await this.openNewAccountButton.click();
    }

    async getNewAccountID() {
        await this.newAccount.waitFor();
        return (await this.newAccount.textContent()).trim();
    }
}