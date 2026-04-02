export function InvalidLogin() {
    return [
        { case: 'Username bỏ trống', username: '', password: 'demo' },
        { case: 'Password bỏ trống', username: 'john', password: '' },
        { case: 'Username và Password bỏ trống', username: '', password: '' },
    ]
}

export function NegativeLogin() {
    return [
        { case: 'Username sai', username: 'john1', password: 'demo' },
        { case: 'Password sai', username: 'john', password: 'demo1' },
        { case: 'Username và Password đều sai', username: 'john1', password: 'demo1' },
    ]
}