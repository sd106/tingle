import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'
import LayoutOnView from '@/views/Layout/LayoutOnView.vue'
import LayoutOnlyHeader from '@/views/Layout/LayoutOnlyHeader.vue'
import LayoutOffView from '@/views/Layout/LayoutOffView.vue'
import HomeView from '@/views/HomeView.vue'
import SignUpView from '@/views/User/SignUpView.vue'
import ProfileView from '@/views/User/ProfileView.vue'
import ProfileUserInfoView from '@/views/User/ProfileUserInfoView.vue'
import ProfileUserStorageView from '@/views/User/ProfileUserStorageView.vue'
import ProfileUserOrdersView from '@/views/User/ProfileUserOrdersView.vue'
import ProfileUserOrdersDetailView from '@/views/User/ProfileUserOrdersDetailView.vue'
import StarHomeMainView from '@/views/Star/StarHome/StarHomeMainView.vue'
import StarHomeManageView from '@/views/Star/StarHome/StarHomeManageView.vue'
import SnapshotMainView from '@/views/Star/Snapshot/SnapshotMainView.vue'
import SnapshotMain4StarView from '@/views/Star/Snapshot/SnapshotMain4StarView.vue'
import SnapshotCreateView from '@/views/Star/Snapshot/SnapshotCreateView.vue'
import SnapshotUpdateView from '@/views/Star/Snapshot/SnapshotUpdateView.vue'
import WishMainView from '@/views/Star/Wish/WishMainView.vue'
import WishCreateView from '@/views/Star/Wish/WishCreateView.vue'
import WishManageView from '@/views/Star/Wish/WishManageView.vue'
import StoreUserMainView from '@/views/Star/Store/StoreUserMainView.vue'
import StoreStarMainView from '@/views/Star/Store/StoreStarMainView.vue'
import StoreCreateView from '@/views/Star/Store/StoreCreateView.vue'
import StoreDetailStarView from '@/views/Star/Store/StoreDetailStarView.vue'
import StoreDetailUserView from '@/views/Star/Store/StoreDetailUserView.vue'
import StoreUpdateView from '@/views/Star/Store/StoreUpdateView.vue'
import StoreStarOrderView from '@/views/Star/Store/StoreStarOrderView.vue'
import FanMeetingEntranceView from '@/views/Star/FanMeeting/FanMeetingEntranceView.vue'
import FanMeetingEntrance4StarView from '@/views/Star/FanMeeting/FanMeetingEntrance4StarView.vue'
import FanMeetingTicketBoxView from '@/views/Star/FanMeeting/FanMeetingTicketBoxView.vue'
import CreateFanMeetingView from '@/views/Star/FanMeeting/CreateFanMeetingView.vue'
import WaitingRoomView from '@/views/Star/FanMeeting/WaitingRoomView.vue'
import MeetingRoomView from '@/views/Star/FanMeeting/MeetingRoomView.vue'
import MeetingRoom4StarView from '@/views/Star/FanMeeting/MeetingRoom4StarView.vue'
import NormalMeetingView from '@/views/Star/FanMeeting/NormalMeeting.vue'
import LifeFourCutMeetingView from '@/views/Star/FanMeeting/LifeFourCutMeeting.vue'
import BirthdayMeetingView from '@/views/Star/FanMeeting/BirthdayMeeting.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // 레이아웃 모두 표시
    {
      path: '',
      name: 'layoutOn',
      component: LayoutOnView,
      children: [
        {
          path: '',
          name: 'home',
          component: HomeView
        },
        // 회원
        {
          path: '/signup',
          name: 'signup',
          component: SignUpView
        },
        {
          path: '/selectLoginType',
          name: 'SelectLoginTypeView',
          component: () => import('@/views/User/SelectLoginTypeView.vue')
        },
        // 프로필
        {
          path: '/profile',
          name: 'profileview',
          component: ProfileView
        },
        {
          path: '/profile/userinfo',
          name: 'profileuserinfoview',
          component: ProfileUserInfoView
        },
        {
          path: '/profile/storage',
          name: 'profileuserstorageview',
          component: ProfileUserStorageView
        },
        {
          path: '/profile/orders',
          name: 'profileuserordersview',
          component: ProfileUserOrdersView
        },
        {
          path: '/:starid/profile/orders/detail/:productId(\\d+)',
          name: 'profileuserordersdetailview',
          component: ProfileUserOrdersDetailView,
          props: true
        },

        // 홈
        {
          path: '/:starid/home',
          name: 'starhome',
          component: StarHomeMainView,
          props: (route) => ({ id: route.params.starid })
        },
        // 스냅샷
        {
          path: '/:starid/snapshot',
          name: 'snapshot',
          component: SnapshotMainView,
          props: (route) => ({ id: route.params.starid })
        },
        {
          path: '/:starid/snapshot/create',
          name: 'snapshotcreate',
          component: SnapshotCreateView,
          props: (route) => ({ id: route.params.starid })
        },
        {
          path: '/:starid/snapshot/:snapshotid/update',
          name: 'snapshotupdate',
          component: SnapshotUpdateView,
          props: true
        },
        // 위시
        {
          path: '/:starid/wish',
          name: 'wish',
          component: WishMainView,
          props: (route) => ({ id: route.params.starid })
        },
        {
          path: '/:starid/wish/create',
          name: 'wishcreate',
          component: WishCreateView,
          props: (route) => ({ id: route.params.starid })
        },
        // 스토어
        {
          path: '/:starid/store/user',
          name: 'storeuserview',
          component: StoreUserMainView,
          props: (route) => ({ id: route.params.starid })
        },
        {
          path: '/:starid/store/:productId(\\d+)',
          name: 'storedetailuserview',
          component: StoreDetailUserView,
          props: true
        },
        // 팬미팅
        {
          path: '/:starid/fanmeeting',
          name: 'fanmeeting',
          component: FanMeetingEntranceView,
          props: (route) => ({ id: route.params.starid })
        },
        {
          path: '/profile/:starid/fanmeeting/:meetingid/ticketbox',
          name: 'FanMeetingTicketBoxView',
          component: FanMeetingTicketBoxView,
          props: (route) => ({ starid: route.params.starid, meetingid: route.params.meetingid })
        },
        {
          path: '/fanmeeting/:starid/waitingroom',
          name: 'WaitingRoomView',
          component: WaitingRoomView
        }
      ]
    },
    // 사이드바 제거
    {
      path: '',
      name: 'layoutonlyheader',
      component: LayoutOnlyHeader,
      children: [
        ////////////////////////스타 메뉴
        ///////////// 스타 전용
        {
          path: '/forstar/home/manage',
          name: 'starhomemanage',
          component: StarHomeManageView
        },
        {
          path: '/forstar/snapshot',
          name: 'starSnapshot',
          component: SnapshotMain4StarView
        },

        {
          path: '/forstar/wish/manage',
          name: 'wishmanage',
          component: WishManageView
        },
        {
          path: '/forstar/store/',
          name: 'storestarmainview',
          component: StoreStarMainView,
          props: (route) => ({ id: route.params.starid })
        },
        {
          path: '/forstar/store/star/:productId(\\d+)',
          name: 'storedetailstarview',
          component: StoreDetailStarView,
          props: true
        },
        {
          path: '/forstar/store/create',
          name: 'storecreate',
          component: StoreCreateView
        },
        {
          path: '/forstar/store/update/:productId',
          name: 'storeupdate',
          component: StoreUpdateView,
          props: true
        },
        {
          path: '/forstar/store/orders',
          name: 'storestarorderview',
          component: StoreStarOrderView
        },
        {
          path: '/forstar/fanmeeting',
          name: 'fanmeeting4star',
          component: FanMeetingEntrance4StarView
        },
        {
          path: '/forstar/fanmeeting/create',
          name: 'CreateMeetingView',
          component: CreateFanMeetingView
        },
        {
          path: '/forstar/fanmeeting/waitingroom',
          name: 'MeetingRoom4StarView',
          component: MeetingRoom4StarView
        },
        //////////////////////////////////////////// 스타 메뉴 끝
        {
          path: '/fanmeetingRoom/:starid',
          name: 'FanMeetingRoomView',
          component: MeetingRoomView,
          children: [
            {
              path: 'normal',
              name: 'NormalFanMeetingView',
              component: NormalMeetingView
            },
            {
              path: 'lifeFourCut',
              name: 'LifeFourCutMeetingView',
              component: LifeFourCutMeetingView
            },
            {
              path: 'birthday',
              name: 'BirthdayMeetingView',
              component: BirthdayMeetingView
            }
          ]
        }
      ]
    },
    // 레이아웃 모두 제거
    {
      path: '',
      name: 'layoutOff',
      component: LayoutOffView,
      children: [
        // {
        //   path: '',
        //   name: '',
        //   component: ??.View,
        // }
        {
          path: '/selectLoginType',
          name: 'SelectLoginTypeView',
          component: () => import('@/views/User/SelectLoginTypeView.vue')
        }
      ]
    }
  ]
})
// 전역
router.beforeEach((to, from, next) => {
  const store = useUserStore()
  const isForStarRoute = to.path.includes('forstar')
  // 스타가 아닌데 스타 전용 페이지를 들어가려고 하면
  if (!store.isStar && isForStarRoute) {
    window.alert('접근할 수 없습니다.')
    next('/') // 홈으로 돌려보내기
    // 스타인데 스타 전용 이외의 페이지로 들어가려고 하면
  } else if (store.isStar && !isForStarRoute) {
    window.alert('접근할 수 없습니다.')
    next('/forstar/home/manage') //스타 홈으로 돌려보냄
  } else {
    next()
  }
})
export default router
