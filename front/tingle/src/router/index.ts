import { createRouter, createWebHistory } from 'vue-router'

import LayoutOnView from '@/views/Layout/LayoutOnView.vue'
import LayoutOnlyHeader from '@/views/Layout/LayoutOnlyHeader.vue'
import LayoutOffView from '@/views/Layout/LayoutOffView.vue'

import HomeView from '@/views/HomeView.vue'

import SignUpView from '@/views/User/SignUpView.vue'
import LogInView from '@/views/User/LogInView.vue'

import ProfileView from '@/views/User/ProfileView.vue'
import ProfileUserInfoView from '@/views/User/ProfileUserInfoView.vue'
import ProfileUserStorageView from '@/views/User/ProfileUserStorageView.vue'
import ProfileUserOrdersView from '@/views/User/ProfileUserOrdersView.vue'
import ProfileUserOrdersDetailView from '@/views/User/ProfileUserOrdersDetailView.vue'

import StarHomeMainView from '@/views/Star/StarHome/StarHomeMainView.vue'
import StarHomeManageView from '@/views/Star/StarHome/StarHomeManageView.vue'

import SnapshotMainView from '@/views/Star/Snapshot/SnapshotMainView.vue'
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
import FanMeetingTicketBoxView from '@/views/Star/FanMeeting/FanMeetingTicketBoxView.vue'
import CreateFanMeetingView from '@/views/Star/FanMeeting/CreateFanMeetingView.vue'
import WaitingRoomView from '@/views/Star/FanMeeting/WaitingRoomView.vue'
import MeetingRoomView from '@/views/Star/FanMeeting/MeetingRoomView.vue'
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
          path: '/login',
          name: 'login',
          component: LogInView
        },
        {
          path: '/:userid/profile',
          name: 'profile',
          component: ProfileView
          // props: (route) => ({ id: route.params.id }),
        },
        //// 스타 메뉴
        // 홈
        {
          path: '/:starid/home',
          name: 'starhome',
          component: StarHomeMainView,
          props: (route) => ({ id: route.params.starid })
        },
        /////// 설정은 어떻게 만들 지 모르겠음
        {
          path: '/:starid/home/manage',
          name: 'starhomemanage',
          component: StarHomeManageView,
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
          path: '/:starid/snapshot/update',
          name: 'snapshotupdate',
          component: SnapshotUpdateView,
          props: (route) => ({ id: route.params.starid })
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
        {
          path: '/:starid/wish/manage',
          name: 'wishmanage',
          component: WishManageView,
          props: (route) => ({ id: route.params.starid })
        },
        // 스토어
        {
          path: '/:starid/store/star',
          name: 'storestarmainview',
          component: StoreStarMainView,
          props: (route) => ({ id: route.params.starid })
        },
        {
          path: '/:starid/store/user',
          name: 'storestaruserview',
          component: StoreUserMainView,
          props: (route) => ({ id: route.params.starid })
        },
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
        {
          path: '/store/orders',
          name: 'storestarorderview',
          component: StoreStarOrderView
        },
        {
          path: '/:starid/store/user/:productId(\\d+)',
          name: 'storedetailuserview',
          component: StoreDetailUserView,
          props: true
        },
        {
          path: '/:starid/store/star/:productId(\\d+)',
          name: 'storedetailstarview',
          component: StoreDetailStarView,
          props: true
        },
        {
          path: '/:starid/store/create',
          name: 'storecreate',
          component: StoreCreateView,
          props: (route) => ({ id: route.params.starid })
        },
        {
          path: '/:starid/store/update/:productId',
          name: 'storeupdate',
          component: StoreUpdateView,
          props: true
        },
        {
          path: '/:starid/fanmeeting',
          name: 'fanmeeting',
          component: FanMeetingEntranceView,
          props: (route) => ({ starid: route.params.starid })
        },
        {
          path: '/fanmeeting/create',
          name: 'CreateMeetingView',
          component: CreateFanMeetingView
        },
        {
          path: '/profile/:starid/fanmeeting/ticketbox',
          name: 'FanMeetingTicketBoxView',
          component: FanMeetingTicketBoxView
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
export default router
