// material-ui
import { Typography } from '@mui/material';

// project import
import MainCard from 'components/MainCard';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
// const brightPopColors = [
//   '#FF5733',
//   '#33FF57',
//   '#3357FF',
//   '#FF33A1',
//   '#33FFF7',
//   '#FFCC00',
//   '#FF007F',
//   '#00FF00',
//   '#FF3300',
//   '#7DFF33',
//   '#33FF5E',
//   '#FF6600',
//   '#FF1493',
//   '#00BFFF',
//   '#8A2BE2',
//   '#FF6347',
//   '#7FFF00',
//   '#00FFFF',
//   '#FF00FF',
//   '#FFD700',
//   '#FF4500',
//   '#ADFF2F',
//   '#FF69B4',
//   '#00FF7F',
//   '#FF8C00',
//   '#4B0082',
//   '#8B00FF',
//   '#FF00FF',
//   '#00FF00',
//   '#FF007F',
//   '#DC143C',
//   '#1E90FF',
//   '#DAA520',
//   '#FF6347',
//   '#32CD32',
//   '#E6E6FA',
//   '#D2691E',
//   '#FF1493',
//   '#00FA9A',
//   '#FF77FF',
//   '#FFB6C1',
//   '#FF00FF',
//   '#7FFF00',
//   '#FF4500',
//   '#00CED1',
//   '#FF6347',
//   '#2E8B57',
//   '#F0E68C',
//   '#B0E0E6',
//   '#FF7F50'
// ];
//
// const getRandomColor = () => {
//   const randomIndex = Math.floor(Math.random() * brightPopColors.length);
//   return brightPopColors[randomIndex];
// };

const SamplePage = () => {
  const navigate = useNavigate();
  useEffect(() => {
    const isLoggedIn = localStorage.getItem('token');
    if (!isLoggedIn) {
      navigate('/login');
      window.location.reload();
    }
  }, [navigate]);

  return (
    <MainCard title="Albums">
      <Typography variant="body2">Albums</Typography>
    </MainCard>
  );
};

export default SamplePage;
