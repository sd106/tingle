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

interface FanState {
    id: number;
    username: string;
    picture: string;
    follwingStars: Starinfo;
}

interface StarState {
    id: number;
    username: string;
    picture: string;
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

interface StarByCategory {
    id: number;
    username: string;
    picture: string;
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

// 위시
interface SnapshotType  {
    id: number;
    imageUrl: string;
    username: string;
  };

interface selectedSnapshotType {
    snapshotId : number;
    imageUrl: string;
    username: string;
    starname: string;
    content: string;
    tags: string[];
    comments: CommentType[];
    likes: number;
    createdAt: string;
    updatedAt: string;
}

interface CommentType {
    id: number;
    context: string;
    username: string;
    snapshotId: number;
};


export type {
    SignUp, LogIn, StarLogininfo, FanState, StarState,
    User, Star, Starinfo, HotStarInfo, StarByCategory,
    // 스토어
    Product, Goods, ProductCreationPayload, ImageUrl, OrderResponse,
    // 팬미팅
    FanMeetingContent, FanMeeting, FanMeetingInfo, FanMeetingTicket,
    SnapshotType, selectedSnapshotType, CommentType

}