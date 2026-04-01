export function payeeData() {
    return {
        name: "Tien dien",
        address: {
            street: "Mai Chi Tho",
            city: "HCM",
            state: "VN",
            zipCode: "12345"
        },
        phoneNumber: "0900112233",
        accountNumber: 556677
    }
}

export function payeeInvalidData() {
    return [
        {
            case: "Tên người thụ hưởng trống",
            data: {
                name: "",
                address: { street: "Mai Chi Tho", city: "HCM", state: "VN", zipCode: "12345" },
                phoneNumber: "0900112233",
                accountNumber: 556677
            }
        },
        {
            case: "Địa chỉ trống",
            data: {
                name: "Tien nuoc",
                address: { street: "", city: "", state: "", zipCode: "" },
                phoneNumber: "0900112233",
                accountNumber: 556677
            }
        },
        {
            case: "Số điện thoại để trống",
            data: {
                name: "Customer HN",
                address: { street: "Mai Chi Tho", city: "HCM", state: "VN", zipCode: "12345" },
                phoneNumber: "",
                accountNumber: 556677
            }
        },
        {
            case: "Số tài khoản thụ hưởng để trống",
            data: {
                name: "Customer SG",
                address: { street: "Mai Chi Tho", city: "HCM", state: "VN", zipCode: "12345" },
                phoneNumber: "0900112233",
                accountNumber: ""
            }
        }
    ];
}