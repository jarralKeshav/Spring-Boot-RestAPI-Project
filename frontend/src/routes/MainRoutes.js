import { lazy } from 'react';

// project import
import Loadable from 'components/Loadable';
import MainLayout from 'layout/MainLayout';

// render - dashboard

// render - sample page
const AlbumsPage = Loadable(lazy(() => import('pages/albums/albums')));
const AboutPage = Loadable(lazy(() => import('pages/staticPages/about')));
const AddAlbumPage = Loadable(lazy(() => import('pages/albums/addAlbums')));

// render - utilities

// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
  path: '/',
  element: <MainLayout />,
  children: [
    {
      path: '/',
      element: <AlbumsPage />
    },
    {
      path: '/add-album',
      element: <AddAlbumPage />
    },
    {
      path: '/about',
      element: <AboutPage />
    }
  ]
};

export default MainRoutes;
