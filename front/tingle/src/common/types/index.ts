interface SignUp {
    username: string;
    password: string;
    email: string;
}

interface LogIn {
    username: string;
    password: string;
}

interface Star {
    name: string;
    image: string;
}

export type {
    SignUp, LogIn,
    Star,
}