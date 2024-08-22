import axios from 'axios';

const BASE_URL = process.env.REACT_APP_BASE_URL;

const fetchGetData = (uri) => {
  const url = `${BASE_URL}${uri}`;
  return axios.get(url).catch((error) => {
    console.error('Error fetching data from url: ', url, 'Error', error.message);
    throw error;
  });
};

const fetchPostData = (uri, payload) => {
  const url = `${BASE_URL}${uri}`;

  return axios.post(url, payload).catch((error) => {
    console.error('Error fetching data from URL:', url, 'Error', error.message);
    throw error;
  });
};

export default fetchGetData;
export { fetchPostData };
