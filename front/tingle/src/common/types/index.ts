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

interface FanMeetingContent {
    name: string
}   

interface FanMeeting {
    name: string;
    ticketStartDate: Date | null 
    ticketEndDate: Date | null
    startDate: Date | null
    participants: number
    price: number
    contents: string[]
}

interface FanMeetingInfo {
    id: number
    status: string
    name: string
    description: string
    ticketingStartAt: string
    ticketingEndAt: string
    fanMeetingStartAt: string
    price: number
    imgURL: string
}

export type {
    SignUp, LogIn,
    Star, 
    FanMeetingContent, FanMeeting, FanMeetingInfo
}