import * as React from 'react';
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';
import { makeStyles } from '@mui/styles';

const useStyles = makeStyles(theme => ({    
    container: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    }
}))
export default function AppPagination({setPageNumber, pageNumber}) {
    const classes = useStyles();
    const handleChange = (pageNumber) => {
        setPageNumber(pageNumber);
        window.scroll(0,0);
    }
  return (
    <Stack spacing={2} className={classes.container}>      
      <Pagination 
        onChange={(e) => handleChange(e.target.textContent)}
        count={10} 
        color="primary" />      
    </Stack>
  );
}