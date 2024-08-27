import React from 'react';
import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import { Link, useLocation } from 'react-router-dom';

const Header = () => {
  const query = new URLSearchParams(useLocation().search);
  const id = query.get('id');

  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Photo Gallery
        </Typography>
        <Button
          component={Link}
          to={`/album/show?id=${id}`}
          color="inherit"
          variant="contained"
          sx={{ mr: 2, bgcolor: '#d933ff', '&:hover': { bgcolor: 'primary.dark' } }}
        >
          Show Photos
        </Button>
        <Button
          component={Link}
          to={`/album/upload?id=${id}`}
          color="inherit"
          variant="contained"
          sx={{ mr: 2, bgcolor: '#00ff00', '&:hover': { bgcolor: 'primary.dark' } }}
        >
          Upload Photo
        </Button>
        <Button
          component={Link}
          to={`/album/delete?id=${id}`}
          color="inherit"
          variant="contained"
          sx={{ mr: 2, bgcolor: '#FF0000', '&:hover': { bgcolor: 'primary.dark' } }}
        >
          Delete Photos
        </Button>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
