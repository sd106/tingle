interface SignUp {
    username: string;
    password: string;
    email: string;
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

interface FanMeetingMessage {
    sender?: string
    text?: string
}

interface SocketMessage {
    sender?: string
    data?: string 
    signalType?: string 
    iceCandidate?: RTCIceCandidateInit 
    sdp?: RTCSessionDescriptionInit 
    roomType?: string 
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

// 스냅샷
interface SnapshotType {
    id: number;
    imageUrl: string;
    username: string;
}

interface selectedSnapshotType {
    snapshotId: number;
    imageUrl: string;
    username: string;
    starname: string;
    content: string;
    tags: string[];
    comments: CommentType[];
    likes: number;
    createdAt: string;
    updatedAt: string;
    isLiked: boolean; // 사용자의 좋아요 상태
}

interface CommentType {
    id: number;
    context: string;
    username: string;
    snapshotId: number;
}

/* 위시 */
interface LikesInfo {
    id: number;
    userId: number;
    wishId: number;
    liked: boolean;
}

interface WishInfo {
    id: number;
    userId: number;
    starId: number;
    status: number;
    points: number;
    likedCount: number;
    contents: string;
    createTime: Date;
    deleteTime: Date;
}

// 채팅
interface ChatRoomInfo {
    id: number;
    starId: number;
    chatMessages: ChatMessageInfo[];
}

interface ChatMessageInfo {
    id: number;
    userId: number;
    direction: number;
    message: string;
    createDate: Date;
    roomId: number;
}

export type {
    SignUp, FanState, StarState,
    User, Star, Starinfo, HotStarInfo, StarByCategory,
    // 스토어
    Goods, ImageUrl, OrderResponse,
    // 팬미팅
    FanMeetingContent, FanMeeting, FanMeetingInfo, FanMeetingTicket,
    Product, ProductCreationPayload, OrderResponse2, UserStoreStorageResponse,
    SnapshotType, selectedSnapshotType, CommentType, FanMeetingMessage,SocketMessage,
    // 위시
    WishInfo, LikesInfo,
    // 채팅
    ChatRoomInfo, ChatMessageInfo
}