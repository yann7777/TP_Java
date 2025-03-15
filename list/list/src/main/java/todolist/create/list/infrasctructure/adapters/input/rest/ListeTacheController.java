import React, { useState, useEffect } from "react";
import { createListeTache, getProjetsByUser, getTachesByUser } from "./loginApi";
import { Container, TextField, Select, MenuItem, Button, Typography, FormControl, InputLabel, CircularProgress } from "@mui/material";
import { List } from "@mui/icons-material";

function CreateListeTacheForm() {
    const [listeTache, setListeTache] = useState({
        description: "",
        etat: "attente",
        idProjet: "",
        idTache: "",
    });

    const [projets, setProjets] = useState([]);
    const [taches, setTaches] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const projetsData = await getProjetsByUser();
                setProjets(projetsData);
                const tachesData = await getTachesByUser();
                setTaches(tachesData);
            } catch (error) {
                console.error("Erreur lors de la récupération des données :", error);
            }
        };
        fetchData();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setListeTache({
            ...listeTache,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setTimeout(async () => {
            try {
                await createListeTache(listeTache);
                alert("Liste de tâches créée !");
            } catch (error) {
                console.error("Erreur API :", error);
                alert("Erreur API : " + error.message);
            } finally {
                setLoading(false);
            }
        }, 5000);
    };

    return (
        <Container maxWidth="sm" sx={{ mt: 4, p: 2, boxShadow: 3, borderRadius: 2, bgcolor: "white", width: "350px" }}>
            <Typography variant="h6" gutterBottom>Créer une liste de tâches</Typography>
            <form onSubmit={handleSubmit}>
                <FormControl fullWidth margin="normal">
                    <TextField
                        label="Description"
                        name="description"
                        value={listeTache.description}
                        onChange={handleInputChange}
                        required
                        InputProps={{ startAdornment: <List /> }}
                    />
                </FormControl>
                <FormControl fullWidth margin="normal">
                    <InputLabel>État</InputLabel>
                    <Select name="etat" value={listeTache.etat} onChange={handleInputChange} required>
                        <MenuItem value="attente">À faire</MenuItem>
                        <MenuItem value="encours">En cours</MenuItem>
                        <MenuItem value="termine">Terminée</MenuItem>
                    </Select>
                </FormControl>
                <FormControl fullWidth margin="normal">
                    <InputLabel>Projet</InputLabel>
                    <Select name="idProjet" value={listeTache.idProjet} onChange={handleInputChange} required>
                        <MenuItem value="">Sélectionnez un projet</MenuItem>
                        {projets.map((projet) => (
                            <MenuItem key={projet.id} value={projet.id}>{projet.nom}</MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <FormControl fullWidth margin="normal">
                    <InputLabel>Tâche</InputLabel>
                    <Select name="idTache" value={listeTache.idTache} onChange={handleInputChange} required>
                        <MenuItem value="">Sélectionnez une tâche</MenuItem>
                        {taches.map((tache) => (
                            <MenuItem key={tache.id} value={tache.id}>{tache.titre}</MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <Button type="submit" variant="contained" color="primary" fullWidth sx={{ mt: 2 }} disabled={loading}>
                    {loading ? <CircularProgress size={24} /> : "Créer la liste de tâches"}
                </Button>
            </form>
        </Container>
    );
}

export default CreateListeTacheForm;
