import React, {useState} from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    TextField,
    Grid
} from '@mui/material';
import {Product} from '../types/Product.ts';

interface EditProductModalProps {
    onSubmit: (product: Product) => Promise<void>; // NOSONAR
    open: boolean,
    onClose: () => void,
    product: Product,
    onSave: (updatedProduct: Product) => void,
    initialData?: Product // NOSONAR
}

const EditProductModal: React.FC<EditProductModalProps> = ({open, onClose, product, onSave}) => {
    const [name, setName] = useState(product.name);
    const [price, setPrice] = useState(product.price);
    const [gpuData, setGpuData] = useState(product.gpu);
    const [motherboardData, setMotherboardData] = useState(product.motherboard);
    const [loading, setLoading] = useState(false)
    const handleSave = () => {
        if (loading) return
        try {
            setLoading(true)
            const updatedProduct: Product = {
                ...product,
                name,
                price,
                gpu: gpuData,
                motherboard: motherboardData
            };
            onSave(updatedProduct);
        }
        catch (error) {
            console.error(error);
        }
        finally {
            setLoading(false)
        }
    };

    return (
        <Dialog open={open} onClose={onClose} fullWidth maxWidth="md">
            <DialogTitle>Edit Product</DialogTitle>
            <DialogContent>
                <Grid container spacing={2} sx={{mt: 1}}>
                    <Grid mx={{xs:"12"}}>
                        <TextField
                            label="Name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            fullWidth
                        />
                    </Grid>
                    <Grid mx={{xs:"12"}}>
                        <TextField
                            label="Price"
                            type="number"
                            value={price}
                            onChange={(e) => setPrice(parseFloat(e.target.value))}
                            fullWidth
                        />
                    </Grid>

                    {gpuData && (
                        <>
                            <Grid mx={{xs:"12"}}>
                                <TextField
                                    label="Producer"
                                    value={gpuData.producer}
                                    onChange={(e) =>
                                        setGpuData({...gpuData, producer: e.target.value})
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="VRAM (GB)"
                                    type="number"
                                    value={gpuData.vram}
                                    onChange={(e) =>
                                        setGpuData({
                                            ...gpuData,
                                            vram: parseInt(e.target.value)
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Boost Clock (MHz)"
                                    type="number"
                                    value={gpuData.boostClock}
                                    onChange={(e) =>
                                        setGpuData({
                                            ...gpuData,
                                            boostClock: parseInt(e.target.value)
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="TDP (W)"
                                    type="number"
                                    value={gpuData.tdp}
                                    onChange={(e) =>
                                        setGpuData({
                                            ...gpuData,
                                            tdp: parseInt(e.target.value)
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="HDMI"
                                    type="number"
                                    value={gpuData.hdmi}
                                    onChange={(e) =>
                                        setGpuData({
                                            ...gpuData,
                                            hdmi: parseInt(e.target.value)
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="DisplayPort"
                                    type="number"
                                    value={gpuData.displayPort}
                                    onChange={(e) =>
                                        setGpuData({
                                            ...gpuData,
                                            displayPort: parseInt(e.target.value)
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="DVI"
                                    type="number"
                                    value={gpuData.dvi}
                                    onChange={(e) =>
                                        setGpuData({
                                            ...gpuData,
                                            dvi: parseInt(e.target.value)
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="VGA"
                                    type="number"
                                    value={gpuData.vga}
                                    onChange={(e) =>
                                        setGpuData({
                                            ...gpuData,
                                            vga: parseInt(e.target.value)
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                        </>
                    )}

                    {motherboardData && (
                        <>
                            <Grid mx={{xs:"12"}}>
                                <TextField
                                    label="Producer"
                                    value={motherboardData.producer}
                                    onChange={(e) =>
                                        setMotherboardData({
                                            ...motherboardData,
                                            producer: e.target.value
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Socket"
                                    value={motherboardData.socket}
                                    onChange={(e) =>
                                        setMotherboardData({
                                            ...motherboardData,
                                            socket: e.target.value
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Chipset"
                                    value={motherboardData.chipset}
                                    onChange={(e) =>
                                        setMotherboardData({
                                            ...motherboardData,
                                            chipset: e.target.value
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Form Factor"
                                    value={motherboardData.formFactor}
                                    onChange={(e) =>
                                        setMotherboardData({
                                            ...motherboardData,
                                            formFactor: e.target.value
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Memory Type"
                                    value={motherboardData.memoryType}
                                    onChange={(e) =>
                                        setMotherboardData({
                                            ...motherboardData,
                                            memoryType: e.target.value
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="RAM Slots"
                                    type="number"
                                    value={motherboardData.ramSlots}
                                    onChange={(e) =>
                                        setMotherboardData({
                                            ...motherboardData,
                                            ramSlots: parseFloat(e.target.value)
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="SATA Ports"
                                    type="number"
                                    value={motherboardData.sata}
                                    onChange={(e) =>
                                        setMotherboardData({
                                            ...motherboardData,
                                            sata: parseFloat(e.target.value)
                                        })
                                    }
                                    fullWidth
                                />
                            </Grid>
                        </>
                    )}
                </Grid>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button onClick={handleSave} variant="contained" color="primary">
                    Save
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default EditProductModal;