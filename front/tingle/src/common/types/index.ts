import type { StringDecoder } from "string_decoder";

interface SignUp {
    username: string;
    password: string;
    email: string;
}

interface LogIn {
    username: string;
    password: string;
}

interface User {
    id: string;
    nickName: string;
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

interface FanMeetingTicket {
    id: number
    image: string
    name: string
    description: string
    price: number
}

interface Star {
    id: string; // tjdah25
    name: string;   // 이성모 
    nickName: string; // 서옴
    image: string; // 프로필 사진
}

interface HotStarInfo{
    id: number;
    username: String;
    picture: String;
}

interface Starinfo {
    id: number;
    username: String;
    picture: String;
}
interface StarLogininfo {
    starId?: number;
    username: string;
    picture?: string;
    email?: string;
    role?: string;
    provider?: string;
}

// 스토어
interface Product {
    starId: number;
    starName: string;
    name: string;
    amount: number;
    price: number;
    content: string;
}
interface ProductCreationPayload {
    productDto: Product;
    files: File[];
}

export type {
    SignUp, LogIn,
    FanMeetingContent, FanMeeting, FanMeetingInfo, FanMeetingTicket,
    User, Star, Starinfo, HotStarInfo,
    Product, ProductCreationPayload, StarLogininfo
}