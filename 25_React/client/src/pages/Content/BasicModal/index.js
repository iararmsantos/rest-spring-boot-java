import * as React from 'react';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';


const modalStyles = {
  wrapper: {
      position: 'absolute',
      top: '50%',
      left: '50%',
      transform: 'translate(-50%, -50%)',
      width: 400,
      bgcolor: 'background.paper',
      boxShadow: 24,
      p: 4,
      borderRadius: 8,
  },
  inputFields: {
      display: 'flex',
      flexDirection: 'column',
      marginTop: '20px',
      marginBottom: '15px',
      '.MuiInput-root': {
          'marginBottom': '20px',
      },
  },
  buttons: {
      display: 'flex',
      justifyContent: 'center',
  }
}

export default function BasicModal({ open, onClose, title, subtitle, content }) {
  
  return (
    <Modal open={open} onClose={onClose}>
      <Box sx={modalStyles.wrapper}>
        <Typography 
          id="modal-modal-title" 
          variant="h6" component="h2"
          sx={{textAlign:'center'}}
        >
          {title}
        </Typography>
        <Typography 
          id="modal-modal-description" 
          sx={{ mt: 2, textAlign:'center' }}
        >
          {subtitle}         
        </Typography>
        {content}        
        
      </Box>
    </Modal>
  );
}