export class TransferFundsPage {
    constructor(page) {
        this.page = page;

        this.transferFundsLink = this.page.getByRole('link', { name: 'Transfer Funds' });
        this.amountInput = this.page.locator('#amount');
        this.fromAccountID = this.page.locator('#fromAccountId');
        this.toAccountID = this.page.locator('#toAccountId');
        this.transferButton = this.page.getByRole('button', { name: 'Transfer' });
        this.successMessage = this.page.getByRole('heading', { name: 'Transfer Complete!' });
        this.errorMessage = this.page.getByRole('heading', { name: 'Error!' })
    }


    async transferFunds(amount, fromAccountId, toAccountId) {
        await this.transferFundsLink.click();
        await this.amountInput.fill(amount.toString());
        await this.fromAccountID.selectOption(fromAccountId);
        await this.toAccountID.selectOption(toAccountId);
        await this.transferButton.click();
    }
}