export function RegisterData() {
    return [
        {
            firstName: 'John',
            lastName: 'Doe',
            address: '123 Main St',
            city: 'New York',
            state: 'NY',
            zipCode: '12345',
            phone: '1234567890',
            ssn: '123456789',
            username: 'tester',
            password: 'tester',
            confirmPassword: 'tester'
        }
    ]
}

export function RegisterInvalidData() {
    return [
        { case: 'First Name bỏ trống', firstName: '', lastName: 'Doe', address: '123 Main St', city: 'New York', state: 'NY', zipCode: '12345', phone: '1234567890', ssn: '123456789', username: 'tester', password: 'password', confirmPassword: 'password', expectedError: 'First name is required.' },
        { case: 'Last Name bỏ trống', firstName: 'John', lastName: '', address: '123 Main St', city: 'New York', state: 'NY', zipCode: '12345', phone: '1234567890', ssn: '123456789', username: 'tester', password: 'password', confirmPassword: 'password', expectedError: 'Last name is required.' },
        { case: 'Address bỏ trống', firstName: 'John', lastName: 'Doe', address: '', city: 'New York', state: 'NY', zipCode: '12345', phone: '1234567890', ssn: '123456789', username: 'tester', password: 'password', confirmPassword: 'password', expectedError: 'Address is required.' },
        { case: 'City bỏ trống', firstName: 'John', lastName: 'Doe', address: '123 Main St', city: '', state: 'NY', zipCode: '12345', phone: '1234567890', ssn: '123456789', username: 'tester', password: 'password', confirmPassword: 'password', expectedError: 'City is required.' },
        { case: 'State bỏ trống', firstName: 'John', lastName: 'Doe', address: '123 Main St', city: 'New York', state: '', zipCode: '12345', phone: '1234567890', ssn: '123456789', username: 'tester', password: 'password', confirmPassword: 'password', expectedError: 'State is required.' },
        { case: 'Zip Code bỏ trống', firstName: 'John', lastName: 'Doe', address: '123 Main St', city: 'New York', state: 'NY', zipCode: '', phone: '1234567890', ssn: '123456789', username: 'tester', password: 'password', confirmPassword: 'password', expectedError: 'Zip Code is required.' },
        { case: 'SSN bỏ trống', firstName: 'John', lastName: 'Doe', address: '123 Main St', city: 'New York', state: 'NY', zipCode: '12345', phone: '1234567890', ssn: '', username: 'tester', password: 'password', confirmPassword: 'password', expectedError: 'Social Security number is required.' },
        { case: 'Username bỏ trống', firstName: 'John', lastName: 'Doe', address: '123 Main St', city: 'New York', state: 'NY', zipCode: '12345', phone: '1234567890', ssn: '123456789', username: '', password: 'password', confirmPassword: 'password', expectedError: 'Username is required.' },
        { case: 'Password bỏ trống', firstName: 'John', lastName: 'Doe', address: '123 Main St', city: 'New York', state: 'NY', zipCode: '12345', phone: '1234567890', ssn: '123456789', username: 'tester', password: '', confirmPassword: 'password', expectedError: 'Password is required.' },
        { case: 'Confirm Password bỏ trống', firstName: 'John', lastName: 'Doe', address: '123 Main St', city: 'New York', state: 'NY', zipCode: '12345', phone: '1234567890', ssn: '123456789', username: 'tester', password: 'password', confirmPassword: '', expectedError: 'Password confirmation is required.' },
        {
            case: 'Bỏ trống tất cả',
            firstName: '', lastName: '', address: '', city: '', state: '', zipCode: '', phone: '', ssn: '', username: '', password: '', confirmPassword: '',
            allErrors: [
                'First name is required.',
                'Last name is required.',
                'Address is required.',
                'City is required.',
                'State is required.',
                'Zip Code is required.',
                'Social Security Number is required.',
                'Username is required.',
                'Password is required.',
                'Password confirmation is required.'
            ]
        },
    ]
}