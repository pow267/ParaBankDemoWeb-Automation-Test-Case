export function NegativeLogin() {
    return [
        {
            case: 'Sai tên đăng nhập',
            username: 'johnn',
            password: 'demo',
        },
        {
            case: 'Sai mật khẩu',
            username: 'john',
            password: '1234',

        },
        {
            case: 'Sai tên đăng nhập và mật khẩu',
            username: 'johnn',
            password: '1234',

        }
    ]
}

export function InvalidLogin() {
    return [
        {
            case: 'tên đăng nhập bỏ trống',
            username: '',
            password: 'demo',
        },
        {
            case: 'mật khẩu bỏ trống',
            username: 'john',
            password: '',

        },
        {
            case: 'Bỏ trống tên đăng nhập và mật khẩu',
            username: '',
            password: '',

        }
    ]
}