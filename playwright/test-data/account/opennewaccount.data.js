export function AccountType() {
    return [
        { case: 'CHECKING', TYPE: '0' },
        { case: 'SAVINGS', TYPE: '1' }
    ]
}

export function fromAccountId() {
    return '12345'
}

export function toAccountId() {
    return '12456'
}