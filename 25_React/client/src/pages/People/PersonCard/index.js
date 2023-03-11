import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import { Box, Button } from '@mui/material';
import { FiEdit, FiTrash2 } from 'react-icons/fi';
import api from "../../../services/api";
import NewPersonModal from '../NewPersonModal';

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));

export default function PersonCard() {
    const [people, setPeople] = useState([]);
    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');
    const [open, setOpen] = useState(false);
    const history = useNavigate();   
    const [personId, setPersonId] = useState();
    const [page, setPage] = useState([]);

    async function editPerson(personId){        
        setPersonId(personId);
        setOpen(true);
    }
    async function deletePeople(id){
        try {
            await api.delete(`api/person/v1/${id}`,{
                headers: {
                    Authorization: `Bearer ${accessToken}`
                },
            })
            setPeople(people.filter(person => person.id !== id));
        } catch (error) {
            alert('Delete failed! Try again.')
            alert(error)
        }
    }

 async function fetchMorePerson() {
        const response = await api.get('api/person/v1', {
            headers: {
                Authorization: `Bearer ${accessToken}`
            },
            params: {
                page: page,
                limit: 6,
                direction: 'asc'
            }
        });
        setPeople([...people, ...response.data._embedded.personVOList]);
        setPage(page+1);
    }

    useEffect(() => {
        fetchMorePerson();
    }, [])
    return (        
            <Box>
                <Grid container>                    
                        {people.map(person => (
                            <Grid 
                                key={person.id} 
                                item xs={6} 
                                md={4} 
                                className="item"
                            >
                            <Item className="subitem">
                            <strong>Name:</strong>
                            <p>{person.firstName} {person.lastName}</p>                            
                            <strong>Email:</strong>
                            <p>{person.email}</p>
                            <strong>Enabled:</strong>
                            <p>{person.enabled.toString()}</p>  
                            {/* here i need to call the <NewPersonModal> that will set data or not*/}
                            <button 
                                onClick={() => editPerson(person.id)} 
                                type='button'>
                                <FiEdit size={20} color="#2596be" 
                            />
                            </button>
                            <button 
                                onClick={() => deletePeople(person.id)} 
                                type='button'>
                                <FiTrash2 size={20} color="#2596be" 
                            />
                            </button>
                        </Item>
                        </Grid>
                        ))}                                   
                </Grid>
                <NewPersonModal 
                personId={personId}
                open={open} 
                onClose={() => setOpen(false)}
                />
                <Button
                    sx={{width:250, marginLeft: 60}}                    
                    size='small'
                    onClick={fetchMorePerson}    
                    className='button' variant="contained">View More People</Button>                
            </Box>        
    );
}