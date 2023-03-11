import React, { useState } from 'react';
import './styles.css'
import PersonCard from './PersonCard';
import { Button } from '@mui/material';
import Header from '../Content/Header';
import NewPersonModal from './NewPersonModal';

export default function People() {
    const [open, setOpen] = useState(false);
    const [personId, setPersonId] = useState();

    const addPerson = (personId) => {
        setPersonId(personId);
        setOpen(true);
    }
    return (
        <div>
            <Header/>
            <div className="person-container">
                <header>
                    <h1>Registered People</h1>
                    <Button
                    sx={{width:250, marginLeft: 60}}                    
                    size='small'
                    onClick={() => addPerson('0')}    
                    className='button' variant="contained">Add Person</Button>
                </header>
                <PersonCard />
                <NewPersonModal
                    personId={personId} 
                    open={open} 
                    onClose={() => setOpen(false)}/>
            </div>            
        </div>
    )
}