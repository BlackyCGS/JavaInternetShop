import React, { useState } from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    TextField,
    Radio,
    RadioGroup,
    FormControlLabel,
    FormControl,
    FormLabel,
    Grid
} from '@mui/material';
import { Product, Gpu, Motherboard } from '../types/Product.ts';

interface CreateProductModalProps {
    open: boolean;
    onClose: () => void;
    onSave: (product: Product) => void;
}

const CreateProductModal: React.FC<CreateProductModalProps> = ({ open, onClose, onSave }) => {
    const [name, setName] = useState('');
    const [price, setPrice] = useState(0);
    const [loading, setLoading] = useState(false)
    const [productType, setProductType] = useState<'gpu' | 'motherboard'>('gpu');
    const [gpuData, setGpuData] = useState<Gpu>({
        displayPort: 0, dvi: 0, hdmi: 0, productId: 0, tdp: 0, vga: 0,
        boostClock: 0,
        producer: '',
        vram: 0,
        name: ''
    });
    const [motherboardData, setMotherboardData] = useState<Motherboard>({
        displayPort: 0,
        dvi: 0,
        formFactor: "",
        hdmi: 0,
        memoryType: "",
        productId: 0,
        ramSlots: 0,
        sata: 0,
        vga: 0,
        producer: '',
        chipset: '',
        socket: '',
        name: ''
    });

    const handleSave = () => {
        if(loading) return
        try {
            setLoading(true)
            gpuData.name = name;
            motherboardData.name = name;
            const baseProduct: Product = {
                name,
                price,
                gpu: productType === 'gpu' ? gpuData : undefined,
                motherboard: productType === 'motherboard' ? motherboardData : undefined,
                id: 0
            };
            onSave(baseProduct);
            onClose();
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
            <DialogTitle>Create Product</DialogTitle>
            <DialogContent>
                <Grid container spacing={2} sx={{ mt: 1 }}>
                    <Grid mx={{xs:"12"}}>
                        <FormControl component="fieldset">
                            <FormLabel component="legend">Product Type</FormLabel>
                            <RadioGroup
                                row
                                value={productType}
                                onChange={(e) => setProductType(e.target.value as 'gpu' | 'motherboard')}
                            >
                                <FormControlLabel value="gpu" control={<Radio />} label="GPU" />
                                <FormControlLabel value="motherboard" control={<Radio />} label="Motherboard" />
                            </RadioGroup>
                        </FormControl>
                    </Grid>

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

                    {productType === 'gpu' ? (
                        <>
                            <Grid mx={{xs:"12"}}>
                                <TextField
                                    label="Producer"
                                    value={gpuData.producer}
                                    onChange={(e) => setGpuData({...gpuData, producer: e.target.value})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="VRAM (GB)"
                                    type="number"
                                    value={gpuData.vram}
                                    onChange={(e) => setGpuData({...gpuData, vram: parseInt(e.target.value)})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Boost Clock (MHz)"
                                    type="number"
                                    value={gpuData.boostClock}
                                    onChange={(e) => setGpuData({...gpuData, boostClock: parseInt(e.target.value)})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="TDP (W)"
                                    type="number"
                                    value={gpuData.tdp}
                                    onChange={(e) => setGpuData({...gpuData, tdp: parseInt(e.target.value)})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="HDMI"
                                    type="number"
                                    value={gpuData.hdmi}
                                    onChange={(e) => setGpuData({...gpuData, hdmi: parseInt(e.target.value)})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="DisplayPort"
                                    type="number"
                                    value={gpuData.displayPort}
                                    onChange={(e) => setGpuData({...gpuData, displayPort: parseInt(e.target.value)})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="DVI"
                                    type="number"
                                    value={gpuData.dvi}
                                    onChange={(e) => setGpuData({...gpuData, dvi: parseInt(e.target.value)})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="VGA"
                                    type="number"
                                    value={gpuData.vga}
                                    onChange={(e) => setGpuData({...gpuData, vga: parseInt(e.target.value)})}
                                    fullWidth
                                />
                            </Grid>
                        </>
                    ) : (
                        <>
                            <Grid mx={{xs:"12"}}>
                                <TextField
                                    label="Producer"
                                    value={motherboardData.producer}
                                    onChange={(e) => setMotherboardData({...motherboardData, producer: e.target.value})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Socket"
                                    value={motherboardData.socket}
                                    onChange={(e) => setMotherboardData({...motherboardData, socket: e.target.value})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Chipset"
                                    value={motherboardData.chipset}
                                    onChange={(e) => setMotherboardData({...motherboardData, chipset: e.target.value})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Form Factor"
                                    value={motherboardData.formFactor}
                                    onChange={(e) => setMotherboardData({...motherboardData, formFactor: e.target.value})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="Memory Type"
                                    value={motherboardData.memoryType}
                                    onChange={(e) => setMotherboardData({...motherboardData, memoryType: e.target.value})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="RAM Slots"
                                    type="number"
                                    value={motherboardData.ramSlots}
                                    onChange={(e) => setMotherboardData({...motherboardData, ramSlots: parseFloat(e.target.value)})}
                                    fullWidth
                                />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField
                                    label="SATA Ports"
                                    type="number"
                                    value={motherboardData.sata}
                                    onChange={(e) => setMotherboardData({...motherboardData, sata: parseFloat(e.target.value)})}
                                    fullWidth
                                />
                            </Grid>
                        </>
                    )}
                </Grid>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button
                    onClick={handleSave}
                    variant="contained"
                    color="primary"
                    disabled={!name || !price}
                >
                    Save
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default CreateProductModal;