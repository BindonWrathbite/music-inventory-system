import { createRouter, createWebHistory } from 'vue-router';
    import HomeView from '@/views/HomeView.vue';
    import InstrumentsView from '@/views/InstrumentsView.vue';

    const routes = [
      {
        path: '/',
        name: 'home',
        component: HomeView,
      },
      {
        path: '/instruments',
        name: 'instruments',
        component: InstrumentsView,
      },
    ];

    const router = createRouter({
      history: createWebHistory(), // Use HTML5 history mode
      routes,
    });

    export default router;