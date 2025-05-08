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
    open: boolean,
    onClose: () => void,
    product: Product,
    onSave: (updatedProduct: Product) => void,
}

const EditProductModal: React.FC<EditProductModalProps> = ({open, onClose, product, onSave}) => {
    const [name, setName] = useState(product.name);
    const [price, setPrice] = useState(product.price);
    const [gpuData, setGpuData] = useState(product.gpu);
    const [motherboardData, setMotherboardData] = useState(product.motherboard);

    const handleSave = () => {
        const updatedProduct: Product = {
            ...product,
            name,
            price,
            gpu: gpuData,
            motherboard: motherboardData
        };
        onSave(updatedProduct);
    };

    return (
        <Dialog open={open} onClose={onClose} fullWidth maxWidth="md">
            <DialogTitle>Редактировать товар</DialogTitle>
            <DialogContent>
                <Grid container spacing={2} sx={{mt: 1}}>
                    <Grid item xs={12}>
                        <TextField
                            label="Название"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            fullWidth
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            label="Цена"
                            type="number"
                            value={price}
                            onChange={(e) => setPrice(parseFloat(e.target.value))}
                            fullWidth
                        />
                    </Grid>

                    {gpuData && (
                        <>
                            <Grid item xs={12}>
                                <TextField
                                    label="Производитель GPU"
                                    value={gpuData.producer}
                                    onChange={(e) =>
                                        setGpuData({...gpuData, producer: e.target.value})
                                    }
                                    fullWidth
                                />
                            </Grid>
                            <Grid item xs={6}>
                                <TextField
                                    label="VRAM (ГБ)"
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
                            <Grid item xs={6}>
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
                            <Grid item xs={6}>
                                <TextField
                                    label="TDP (Вт)"
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
                            <Grid item xs={6}>
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
                            <Grid item xs={6}>
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
                            <Grid item xs={6}>
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
                            <Grid item xs={6}>
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
                            <Grid item xs={12}>
                                <TextField
                                    label="Производитель Материнской платы"
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
                            <Grid item xs={6}>
                                <TextField
                                    label="Сокет"
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
                            <Grid item xs={6}>
                                <TextField
                                    label="Чипсет"
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
                            <Grid item xs={6}>
                                <TextField
                                    label="Форм-фактор"
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
                            <Grid item xs={6}>
                                <TextField
                                    label="Тип памяти"
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
                            <Grid item xs={6}>
                                <TextField
                                    label="Слоты под RAM"
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
                            <Grid item xs={6}>
                                <TextField
                                    label="SATA порты"
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
                <Button onClick={onClose}>Отмена</Button>
                <Button onClick={handleSave} variant="contained" color="primary">
                    Сохранить
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default EditProductModal;