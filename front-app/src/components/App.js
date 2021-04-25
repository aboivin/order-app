import './App.css';
import React, {useRef, useState} from 'react';
import SockJsClient from 'react-stomp';
import OrderTable from "./OrderTable";
import {Button} from "@material-ui/core";
import OrderCreationModal from "./modal/OrderCreationModal";
import {Alert, AlertTitle} from "@material-ui/lab";

const App = () => {

    const clientEl = useRef(null);

    const [orders, setOrders] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");
    const [displayCreationModal, setDisplayCreationModal] = useState(false);

    const createOrder = (customerId) => {
        clientEl.current.sendMessage('/app/orders/create', JSON.stringify({ customerId: customerId }));
    }

    const addItem = (orderId, name, price) => {
        clientEl.current.sendMessage('/app/orders/addItem', JSON.stringify({orderId, name, price}));
    }

    return (
        <div className="App">
            <SockJsClient url='http://localhost:8080/websocket'
                          topics={['/app/orders', '/topic/orders.updates']}
                          onMessage={(msg) => {
                              console.log(msg);
                              setOrders(msg);
                          }}
                          ref={clientEl}/>
            <SockJsClient url='http://localhost:8080/websocket'
                          topics={['/user/queue/error']}
                          onMessage={(msg) => {
                              console.log('Error ', msg);
                              setErrorMessage(msg);
                              setTimeout(() => setErrorMessage(''), 3000);
                          }}
                          ref={clientEl}/>
            { errorMessage && <Alert severity="warning" className='alert'>
                <AlertTitle>Warning</AlertTitle>
                { errorMessage }
            </Alert> }
        <div className="App-content">
            <div className="order-panel">
                <div className='button-bar'>
                    <Button variant="contained" color="primary" onClick={() => setDisplayCreationModal(true)}>
                        Create order</Button>
                </div>
                <OrderTable onItemAdded={addItem} orders={orders}/>
            </div>
            <OrderCreationModal open={displayCreationModal}
                                onClose={() => setDisplayCreationModal(false)}
                                onCreate={(customerId) => {
                                    createOrder(customerId);
                                    setDisplayCreationModal(false);
                                }}
            />
        </div>
        </div>
    );
}

export default App;
