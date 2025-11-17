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
import { Product, Gpu, Motherboard, Cpu, Hdd, Ssd, Ram, Psu, PcCase } from '../types/Product.ts';

interface CreateProductModalProps {
    open: boolean;
    onClose: () => void;
    onSave: (product: Product) => void;
}

type ProductType = 'gpu' | 'motherboard' | 'cpu' | 'hdd' | 'ssd' | 'ram' | 'psu' | 'pcCase';

const CreateProductModal: React.FC<CreateProductModalProps> = ({ open, onClose, onSave }) => {
    const [productType, setProductType] = useState<ProductType>('gpu');
    const [name, setName] = useState('');
    const [price, setPrice] = useState<number>(0);
    const [stock, setStock] = useState<number>(0);
    const [loading, setLoading] = useState(false);
    const [rating] = useState<number>(0);
    const [ratingAmount] = useState<number>(0);

    // Состояния для каждого типа
    const [gpuData, setGpuData] = useState<Gpu>({
        productId: 0, name: '', producer: '', boostClock: 0, vram: 0, tdp: 0,
        hdmi: 0, displayPort: 0, dvi: 0, vga: 0
    });
    const [motherboardData, setMotherboardData] = useState<Motherboard>({
        productId: 0, name: '', producer: '', socket: '', chipset: '', formFactor: '',
        memoryType: '', sata: 0, ramSlots: 0, vga: 0, dvi: 0, displayPort: 0, hdmi: 0
    });
    const [cpuData, setCpuData] = useState<Cpu>({
        productId: 0, name: '', producer: '', socket: '', cores: 0, threads: 0, tdp: 0
    });
    const [hddData, setHddData] = useState<Hdd>({ productId: 0, name: '', producer: '', size: 0 });
    const [ssdData, setSsdData] = useState<Ssd>({
        productId: 0, name: '', producer: '', formFactor: '', protocol: '', size: 0
    });
    const [ramData, setRamData] = useState<Ram>({
        productId: 0, name: '', producer: '', ramType: '', size: 0, timings: ''
    });
    const [psuData, setPsuData] = useState<Psu>({
        productId: 0, name: '', producer: '', watt: 0, size: '', efficiencyRating: ''
    });
    const [pcCaseData, setPcCaseData] = useState<PcCase>({
        productId: 0, name: '', producer: '', motherboard: '', powerSupply: ''
    });

    const handleSave = () => {
        if (loading) return;
        setLoading(true);


        const newProduct: Product = {
            id: 0,
            name,
            price,
            stock,
            rating,
            ratingAmount,
            gpu: productType === 'gpu' ? gpuData : undefined,
            motherboard: productType === 'motherboard' ? motherboardData : undefined,
            cpu: productType === 'cpu' ? cpuData : undefined,
            hdd: productType === 'hdd' ? hddData : undefined,
            ssd: productType === 'ssd' ? ssdData : undefined,
            ram: productType === 'ram' ? ramData : undefined,
            psu: productType === 'psu' ? psuData : undefined,
            pcCase: productType === 'pcCase' ? pcCaseData : undefined
        };

        onSave(newProduct);
        setLoading(false);
        onClose();
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
                                onChange={(e) => setProductType(e.target.value as ProductType)}
                            >
                                {['gpu','motherboard','cpu','hdd','ssd','ram','psu','pcCase'].map(pt => (
                                    <FormControlLabel key={pt} value={pt} control={<Radio />} label={pt.toUpperCase()} />
                                ))}
                            </RadioGroup>
                        </FormControl>
                    </Grid>

                    <Grid mx={{xs:"12"}}>
                        <TextField label="Name" value={name} onChange={e => setName(e.target.value)} fullWidth />
                    </Grid>
                    <Grid mx={{xs:"6"}}>
                        <TextField label="Price" type="number" value={price} onChange={e => setPrice(parseFloat(e.target.value))} fullWidth />
                    </Grid>
                    <Grid mx={{xs:"6"}}>
                        <TextField label="Stock" type="number" value={stock} onChange={e => setStock(parseInt(e.target.value))} fullWidth />
                    </Grid>

                    {/* Поля по типам */}
                    {productType === 'gpu' && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Producer" value={gpuData.producer} onChange={e => setGpuData({...gpuData, producer: e.target.value})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="VRAM" type="number" value={gpuData.vram} onChange={e => setGpuData({...gpuData, vram: parseInt(e.target.value)})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Boost Clock" type="number" value={gpuData.boostClock} onChange={e => setGpuData({...gpuData, boostClock: parseInt(e.target.value)})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="TDP" type="number" value={gpuData.tdp} onChange={e => setGpuData({...gpuData, tdp: parseInt(e.target.value)})} fullWidth />
                            </Grid>
                        </>
                    )}

                    {productType === 'motherboard' && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Producer" value={motherboardData.producer} onChange={e => setMotherboardData({...motherboardData, producer: e.target.value})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Socket" value={motherboardData.socket} onChange={e => setMotherboardData({...motherboardData, socket: e.target.value})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Chipset" value={motherboardData.chipset} onChange={e => setMotherboardData({...motherboardData, chipset: e.target.value})} fullWidth />
                            </Grid>
                        </>
                    )}

                    {productType === 'cpu' && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Producer" value={cpuData.producer} onChange={e => setCpuData({...cpuData, producer: e.target.value})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Socket" value={cpuData.socket} onChange={e => setCpuData({...cpuData, socket: e.target.value})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Cores" type="number" value={cpuData.cores} onChange={e => setCpuData({...cpuData, cores: parseInt(e.target.value)})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Threads" type="number" value={cpuData.threads} onChange={e => setCpuData({...cpuData, threads: parseInt(e.target.value)})} fullWidth />
                            </Grid>
                        </>
                    )}

                    {productType === 'hdd' && (
                        <Grid mx={{xs:"6"}}>
                            <TextField label="Size (GB)" type="number" value={hddData.size} onChange={e => setHddData({...hddData, size: parseInt(e.target.value)})} fullWidth />
                        </Grid>
                    )}

                    {productType === 'ssd' && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Form Factor" value={ssdData.formFactor} onChange={e => setSsdData({...ssdData, formFactor: e.target.value})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Protocol" value={ssdData.protocol} onChange={e => setSsdData({...ssdData, protocol: e.target.value})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Size (GB)" type="number" value={ssdData.size} onChange={e => setSsdData({...ssdData, size: parseInt(e.target.value)})} fullWidth />
                            </Grid>
                        </>
                    )}

                    {productType === 'ram' && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="RAM Type" value={ramData.ramType} onChange={e => setRamData({...ramData, ramType: e.target.value})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Size (GB)" type="number" value={ramData.size} onChange={e => setRamData({...ramData, size: parseInt(e.target.value)})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Timings" value={ramData.timings} onChange={e => setRamData({...ramData, timings: e.target.value})} fullWidth />
                            </Grid>
                        </>
                    )}

                    {productType === 'psu' && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Watt" type="number" value={psuData.watt} onChange={e => setPsuData({...psuData, watt: parseInt(e.target.value)})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Size" value={psuData.size} onChange={e => setPsuData({...psuData, size: e.target.value})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Efficiency Rating" value={psuData.efficiencyRating} onChange={e => setPsuData({...psuData, efficiencyRating: e.target.value})} fullWidth />
                            </Grid>
                        </>
                    )}

                    {productType === 'pcCase' && (
                        <>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Motherboard Compatibility" value={pcCaseData.motherboard} onChange={e => setPcCaseData({...pcCaseData, motherboard: e.target.value})} fullWidth />
                            </Grid>
                            <Grid mx={{xs:"6"}}>
                                <TextField label="Power Supply Compatibility" value={pcCaseData.powerSupply} onChange={e => setPcCaseData({...pcCaseData, powerSupply: e.target.value})} fullWidth />
                            </Grid>
                        </>
                    )}
                </Grid>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button onClick={handleSave} variant="contained" color="primary" disabled={!name || !price}>
                    Save
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default CreateProductModal;
