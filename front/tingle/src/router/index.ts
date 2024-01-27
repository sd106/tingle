import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'

import SignUpView from '@/views/User/SignUpView.vue'
import LogInView from '@/views/User/LogInView.vue'
import ProfileView from '@/views/User/ProfileView.vue'

import SnapshotView from '@/views/StarMenu/SnapshotView.vue'
import WishView from '@/views/StarMenu/WishView.vue'
import StoreView from '@/views/StarMenu/StoreView.vue'
import FanMeetingEntranceView from '@/views/StarMenu/FanMeeting/FanMeetingEntranceView.vue'

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
      path: '/profile/:username/home',
      name: 'profile',
      component: ProfileView,
      props: (route) => ({ username: route.params.username }),
    },
    // 스타 프로필
    {
      path: '/profile/:username/snapshot',
      name: 'snapshot',
      component: SnapshotView,
      props: (route) => ({ username: route.params.username }),
    },
    {
      path: '/profile/:username/wish',
      name: 'wish',
      component: WishView,
      props: (route) => ({ username: route.params.username }),
    },
    {
      path: '/profile/:username/store',
      name: 'store',
      component: StoreView,
      props: (route) => ({ username: route.params.username }),
    },
    {
      path: '/profile/:username/fanmeeting',
      name: 'fanmeeting',
      component: FanMeetingEntranceView,
      props: (route) => ({ username: route.params.username }),
    },
    {
      path: '/fanmeeting/create',
      name: 'CreateMeetingView',
      component: () => import('@/views/StarMenu/FanMeeting/CreateFanMeetingView.vue'),
    },
    {
      path: '/profile/:username/fanmeeting/ticketbox',
      name: 'FanMeetingTicketBoxView',
      component: () => import('@/views/StarMenu/FanMeeting/FanMeetingTicketBoxView.vue'),
    }
  ]
})

export default router
