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

interface HotStarInfo {
    id: number;
    username: string;
    picture: string;
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

interface Starinfo {
    id: string;
    nickName: string;
    image: string;
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

interface Goods {
    productId: number;
    starId: null | number;
    starName: null | string;
    name: string;
    amount: number;
    price: number;
    imageUrl: ImageUrl[];
    content: string;
    available: boolean;
}

interface ImageUrl {
    id: number;
    url: string;
    productId: Goods;
}

interface OrderResponse {
    productService: null;
    userService: null;
    fanId: null;
    fan: User;
    goods: Goods;
}

interface ProductCreationPayload {
    productDto: Product;
    files: File[];
}


interface ImageUrl {
    id: number;
    url: string;
    productId: Goods;
  }
  

interface Goods {
    productId: number;
    starId: null | number;
    starName: null | string;
    name: string;
    amount: number;
    price: number;
    imageUrl: ImageUrl[];
    content: string;
    available: boolean;
  }

interface UserStoreStorageResponse {
    
    id : number,
    storageId : number,
    url : string,
    title : string,
    content : string,
    username : string,
    starname : string,
    userId : number,
    starId : number, 
}



interface OrderResponse {
    productService: null,
    userService: null,
    fanId: null,
    User : UserOauth,
    goods : Goods,
    orderId : number,
}

interface UserOauth {
    id : number,
    username : string,
    password : string,
    picture : string,
    role: String,
    provide : string,
    email : String,
    roleKey : string,
}

interface OrderResponse2 {
    productService: null;
    userService: null;
    fanId: number;
    fan : UserOauth;
    goods : Goods;
    orderId : number,
}


export type {
    SignUp, LogIn, StarLogininfo, HotStarInfo,
    // 스토어
    Goods, ImageUrl, OrderResponse,
    // 팬미팅
    FanMeetingContent, FanMeeting, FanMeetingInfo, FanMeetingTicket,
    User, Star, Starinfo,
    Product, ProductCreationPayload, OrderResponse2, UserStoreStorageResponse
}