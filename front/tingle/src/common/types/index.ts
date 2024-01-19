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
    username: string;
    name: string;
    image: string;
}

export type {
    SignUp, LogIn,
    Star,
}