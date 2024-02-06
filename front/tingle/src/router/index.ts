import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'

import SignUpView from '@/views/User/SignUpView.vue'
import LogInView from '@/views/User/LogInView.vue'
import ProfileView from '@/views/User/ProfileView.vue'

import StarHomeMainView from '@/views/Star/StarHome/StarHomeMainView.vue'
import StarHomeManageView from '@/views/Star/StarHome/StarHomeManageView.vue'

import SnapshotMainView from '@/views/Star/Snapshot/SnapshotMainView.vue'
import SnapshotCreateView from '@/views/Star/Snapshot/SnapshotCreateView.vue'
import SnapshotUpdateView from '@/views/Star/Snapshot/SnapshotUpdateView.vue'

import WishMainView from '@/views/Star/Wish/WishMainView.vue'
import WishCreateView from '@/views/Star/Wish/WishCreateView.vue'
import WishUpdateView from '@/views/Star/Wish/WishUpdateView.vue'
import WishManageView from '@/views/Star/Wish/WishManageView.vue'

import StoreMainView from '@/views/Star/Store/StoreMainView.vue'
import StoreManageView from '@/views/Star/Store/StoreManageView.vue'
import StoreDetailView from '@/views/Star/Store/StoreDetailView.vue'

import FanMeetingEntranceView from '@/views/Star/FanMeeting/FanMeetingEntranceView.vue'
import FanMeetingTicketBoxView from '@/views/Star/FanMeeting/FanMeetingTicketBoxView.vue'
import CreateFanMeetingView from '@/views/Star/FanMeeting/CreateFanMeetingView.vue'
import WaitingRoomView from '@/views/Star/FanMeeting/WaitingRoomView.vue'
import MeetingRoomView from '@/views/Star/FanMeeting/MeetingRoomView.vue'
import NormalMeetingView from '@/views/Star/FanMeeting/NormalMeeting.vue'
import LifeFourCutMeetingView from '@/views/Star/FanMeeting/LifeFourCutMeeting.vue'
import BirthdayMeetingView from '@/views/Star/FanMeeting/BirthdayMeeting.vue'

import TestStoreViewVue from '@/views/StarMenu/TestStoreView.vue'
import TestStoreViewOrderVue from '@/views/StarMenu/TestStoreViewOrder.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
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
      component: ProfileView,
      // props: (route) => ({ id: route.params.id }),
    },
    //// 스타 메뉴
    // 홈
    {
      path: '/:starid/home',
      name: 'starhome',
      component: StarHomeMainView,
      props: (route) => ({ id: route.params.starid }),
    },
    // 설정은 어떻게 만들 지 모르겠음
    {
      path: '/:starid/home/manage',
      name: 'starhomemanage',
      component: StarHomeManageView,
      props: (route) => ({ id: route.params.starid }),
    },

    // 스냅샷
    {
      path: '/:starid/snapshot',
      name: 'snapshot',
      component: SnapshotMainView,
      props: (route) => ({ id: route.params.starid }),
    },
    {
      path: '/:starid/snapshot/create',
      name: 'snapshotcreate',
      component: SnapshotCreateView,
      props: (route) => ({ id: route.params.starid }),
    },
    {
      path: '/:starid/snapshot/update',
      name: 'snapshotupdate',
      component: SnapshotUpdateView,
      props: (route) => ({ id: route.params.starid }),
    },
    // 위시
    {
      path: '/:starid/wish',
      name: 'wish',
      component: WishMainView,
      props: (route) => ({ id: route.params.starid }),
    },
    {
      path: '/:starid/wish/create',
      name: 'wishcreate',
      component: WishCreateView,
      props: (route) => ({ id: route.params.starid }),
    },
    {
      path: '/:starid/wish/update',
      name: 'wishupdate',
      component: WishUpdateView,
      props: (route) => ({ id: route.params.starid }),
    },
    {
      path: '/:starid/wish/manage',
      name: 'wishmanage',
      component: WishManageView,
      props: (route) => ({ id: route.params.starid }),
    },


    // 스토어
    {
      path: '/:starid/store',
      name: 'store',
      component: StoreMainView,
      props: (route) => ({ id: route.params.starid }),
    },
    {
      path: '/:starid/store/:productId(\\d+)',
      name: 'storedetail',
      component: StoreDetailView,
      props: true
    },
    {
      path: '/:starid/store/manage',
      name: 'storemange',
      component: StoreManageView,
      props: (route) => ({ id: route.params.starid }),
    },


    {
      path: '/:starid/fanmeeting',
      name: 'fanmeeting',
      component: FanMeetingEntranceView,
      props: (route) => ({ starid: route.params.starid }),
    },
    {
      path: '/fanmeeting/create',
      name: 'CreateMeetingView',
      component: CreateFanMeetingView,
    },
    {
      path: '/profile/:starid/fanmeeting/ticketbox',
      name: 'FanMeetingTicketBoxView',
      component: FanMeetingTicketBoxView,
    },
    {
      path: '/fanmeeting/:starid/waitingroom',
      name: 'WaitingRoomView',
      component: WaitingRoomView
    },
    // Store의 test를 위한 View
    {
      path: '/profile/:username/teststore',
      name: 'teststore',
      component: TestStoreViewVue,
      props: (route) => ({ username: route.params.username }),
    },
    {
      path: '/profile/:username/teststore2',
      name: 'teststore2',
      component: TestStoreViewOrderVue,
      props: (route) => ({ username: route.params.username }),
    },
    {
      path: '/selectLoginType',
      name: 'SelectLoginTypeView',
      component: () => import("@/views/User/SelectLoginTypeView.vue")
    },
    {
      path: '/fanmeetingRoom/:starid',
      name: 'FanMeetingRoomView',
      component: MeetingRoomView,
      children: [
        {
          path: 'normal',
          name: 'NormalFanMeetingView',
          component: NormalMeetingView,
        },
        {
          path: 'lifeFourCut',
          name: 'LifeFourCutMeetingView',
          component: LifeFourCutMeetingView,
        },
        {
          path: 'birthday',
          name: 'BirthdayMeetingView',
          component: BirthdayMeetingView,
        }
      ]
    }
  ]
})

export default router
