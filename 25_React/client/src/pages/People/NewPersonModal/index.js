import React, { useState, useEffect } from 'react'
import BasicModal from '../../Content/BasicModal'
import { Input, TextField } from '@mui/material';
import Box from '@mui/material/Box';
import api from "../../../services/api";
import { useNavigate, useParams } from "react-router-dom";
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import { Button } from '@mui/material';
import Stack from '@mui/material/Stack';

const modalStyles = {
    inputFields: {
        display: 'flex',
        flexDirection: 'column',
        marginTop: '20px',
        marginBottom: '20px',
        '.MuiTextField-root': {
            'marginBottom': '20px',
        },
    },
    buttons: {
        display: 'inline',
        alignItems: "center",
        justifyContent: "center",
        marginLeft: 10,
        marginTop: 2,
    },
};
const NewPersonModal = ({ personId, open, onClose }) => {    
    const [id, setId] = useState(null);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [enabled, setEnabled] = useState('');
    const [openModal, setOpenModal] = useState(open)

    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    const history = useNavigate();

    useEffect(() => {
        if (personId === '0') return
        else if (personId === undefined) setId('0')
        else loadPerson();
    }, [personId]);

    async function loadPerson() {
        try {
            const response = await api.get(`api/person/v1/${personId}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })            
            setId(response.data.id);
            setFirstName(response.data.firstName);
            setLastName(response.data.lastName);
            setEmail(response.data.email);           
            setEnabled(response.data.enabled);            
        } catch (error) {
            alert("Error recovering person! Try again.");            
            
        }
    }

    async function saveOrUpdate(e) {
        e.preventDefault();        
        const data = {
            firstName,
            lastName,
            email,
            enabled,
        };             
        try {            
            if(personId === '0'){
                await api.post('api/person/v1', data, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                });
            }else{
                data.id = personId;        
                await api.put('api/person/v1', data, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                });
            }
            alert("Action Completed Successfully!")
            setOpenModal(onClose);
            window.location.reload(false);        
        } catch (error) {
            alert('Error while recording person! Try again');          
        }
    }

    const getContent = () => (
        <Box sx={modalStyles.inputFields}>
            <TextField
                placeholder="First Name"
                name="firstName"
                label="First Name"
                value={firstName}
                onChange={e => setFirstName(e.target.value)}
                required
            />
            <TextField
                value={lastName}
                onChange={e => setLastName(e.target.value)}
                placeholder="Last Name"
                name="lastName"
                label="Last Name"
                required
            />
            <TextField
                value={email}
                onChange={e => setEmail(e.target.value)}
                placeholder="Email"
                name="email"
                label="Email"
                required />
            <RadioGroup
                row
                aria-labelledby="demo-radio-buttons-group-label"
                value={enabled}
                name="radio-buttons-group"
                onChange={e => setEnabled(e.target.value)}                
            >
                <FormControlLabel
                    value="true"
                    control={<Radio />} 
                    label="Enabled" />
                <FormControlLabel
                    value="false"
                    control={<Radio />} 
                    label="Disabled" />
            </RadioGroup>
            <Box
                sx={modalStyles.buttons}>
                <Button
                    variant="contained"
                    onClick={saveOrUpdate}
                >
                    Submit
                </Button>
                <Button
                    sx={{ marginLeft: 2 }}
                    onClick={onClose}>
                    Cancel
                </Button>
            </Box>
        </Box>
    )
    return (
        <BasicModal
            open={open}
            onClose={onClose}
            title="Person Details"
            subtitle=" Fill out inputs and hit 'submit' button"
            content={getContent()}
        >
        </BasicModal>
    )
}

export default NewPersonModal;