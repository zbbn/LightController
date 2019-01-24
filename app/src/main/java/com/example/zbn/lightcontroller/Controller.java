package com.example.zbn.lightcontroller;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;


import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

public class Controller {
   private String TAG3 = "MQQTSUBCRIBE";
    private String TAG2 = "MQQTClientConnect";
    public String topic1 = "hej1"; //Lampor

    public String clientId = MqttClient.generateClientId();
    public MqttConnectOptions options = new MqttConnectOptions();
    public MqttAndroidClient client;
    public IMqttToken token;

    public int lampNbr = -1;
    public int lampState = -1;
    public int lampBrightness = 0;
    public int lampHue = 0;

    public String lampRequest;

    public Controller(Context context, final String lampRequest) {
        this.lampRequest = lampRequest;
        //final MqttAndroidClient client = new MqttAndroidClient(this.getApplicationContext(), "tcp://m20.cloudmqtt.com:14364", clientId);
        client = new MqttAndroidClient(context, "tcp://m20.cloudmqtt.com:14364", clientId);
        options.setUserName("idkbgaxz");
        options.setPassword("3rqjQD0ZElLb".toCharArray());


        // for testing
        //client = new MqttAndroidClient(context, "tcp://m20.cloudmqtt.com:19781", clientId);
        //options.setUserName("mquygdwx");
        //options.setPassword("UqMtCAhpXKaS".toCharArray());

        //Connect to MQTT Client
        try {
            token = client.connect(options);


            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    byte[] encodedPayload = new byte[0];
                    String message = lampRequest;
                    try {
                        encodedPayload = message.getBytes("UTF-8");
                        MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                        client.publish(topic1, mqttMessage);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (MqttPersistenceException e) {
                        e.printStackTrace();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    // We are connected
                    Log.d(TAG2, "onSuccess");
                    try {
                        IMqttToken subToken = client.subscribe("lamp1", 1);

                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken iMqttToken) {
                                client.setCallback(new MqttCallback() {
                                    @Override
                                    public void connectionLost(Throwable throwable) {
                                        //tvSuccess.setText("Connection LOST");
                                        Log.d(TAG3, "connection lost");
                                    }

                                    @Override
                                    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                                        //tvSuccess.append("TOPIC: " + s + " \nMESSAGE: " + new String(mqttMessage.getPayload()) + "\n");
                                        Log.d(TAG3,"MESSAGEs: " + new String(mqttMessage.getPayload()) );
                                        getLampInfo(new String(mqttMessage.getPayload()));
                                    }

                                    @Override
                                    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                                //tvFailure.setText(throwable.getMessage());
                                Log.d(TAG3, "Subscribe failed....");
                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG2, "onFailure" + exception.getMessage());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void getLampInfo(String values) {
        StringTokenizer st = new StringTokenizer(values, ",");
        lampNbr = Integer.parseInt(st.nextToken());
        lampState = Integer.parseInt(st.nextToken());
        lampBrightness = Integer.parseInt(st.nextToken());
        lampHue = Integer.parseInt(st.nextToken());
    }

    public void decreaseLampHue(int lampNumber) {
        byte[] encodedPayload = new byte[0];
        int decHue = -1400;

        if(lampNbr != -1 && lampNbr == lampNumber && lampState == 1) {
            Log.d("VALUES", "NOT NULL");
            if(lampHue >= 1401) {
                lampHue = lampHue + decHue;
            } else {
                lampHue = 0;
            }
            String message = lampNbr + "," + lampState + "," + lampBrightness + "," + lampHue;
            try {
                encodedPayload = message.getBytes("UTF-8");
                MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                client.publish(topic1, mqttMessage);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MqttPersistenceException e) {
                e.printStackTrace();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void increaseLampHue(int lampNumber) {
        byte[] encodedPayload = new byte[0];
        int incHue = 1400;

        if(lampNbr != -1 && lampNbr == lampNumber && lampState == 1) {
            Log.d("VALUES", "NOT NULL");
            if(lampHue <= 12600) {
                lampHue = lampHue + incHue;
            } else {
                lampHue = 14000;
            }
            String message = lampNbr + "," + lampState + "," + lampBrightness + "," + lampHue;
            try {
                encodedPayload = message.getBytes("UTF-8");
                MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                client.publish(topic1, mqttMessage);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MqttPersistenceException e) {
                e.printStackTrace();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void decreaseLampBrightness(int lampNumber) {
        byte[] encodedPayload = new byte[0];
        int decBrightness = -25;

        if(lampNbr != -1 && lampNbr == lampNumber && lampState == 1) {
            if(lampBrightness >= 50) {
                lampBrightness = lampBrightness + decBrightness;
            } else {
                lampBrightness = 25;
            }
            String message = lampNbr + "," + lampState + "," + lampBrightness + "," + lampHue;
            try {
                encodedPayload = message.getBytes("UTF-8");
                MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                client.publish(topic1, mqttMessage);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MqttPersistenceException e) {
                e.printStackTrace();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void increaseLampBrightness(int lampNumber) {
        byte[] encodedPayload = new byte[0];
        int incBrightness = 25;

        if(lampNbr != -1 && lampNbr == lampNumber && lampState == 1) {
            if(lampBrightness <= 250) {
                lampBrightness = lampBrightness + incBrightness;
            } else {
                lampBrightness = 250;
            }
            String message = lampNbr + "," + lampState + "," + lampBrightness + "," + lampHue;
            try {
                encodedPayload = message.getBytes("UTF-8");
                MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                client.publish(topic1, mqttMessage);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MqttPersistenceException e) {
                e.printStackTrace();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void lampOff(int lampNumber) {
        byte[] encodedPayload = new byte[0];

        if(lampNbr != -1 && lampNbr == lampNumber) {
            if(lampState == 1) {
                lampState = 0;

                String message = lampNbr + "," + lampState + "," + lampBrightness + "," + lampHue;
                try {
                    encodedPayload = message.getBytes("UTF-8");
                    MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                    client.publish(topic1, mqttMessage);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MqttPersistenceException e) {
                    e.printStackTrace();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void lampOn(int lampNumber) {
        byte[] encodedPayload = new byte[0];

        if(lampNbr != -1 && lampNbr == lampNumber) {
            if(lampState == 0) {
                lampState = 1;

                String message = lampNbr + "," + lampState + "," + lampBrightness + "," + lampHue;
                try {
                    encodedPayload = message.getBytes("UTF-8");
                    MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                    client.publish(topic1, mqttMessage);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MqttPersistenceException e) {
                    e.printStackTrace();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Below handles all lamps
    public void decreaseAllLampHue() {
        int i = 1;
        int decHue = -1400;
        if(lampState == 1) {
            if(lampHue >= 1401) {
                lampHue = lampHue + decHue;
            } else {
                lampHue = 0;
            }
            while (i <= 3) {
                byte[] encodedPayload = new byte[0];
                String message = i + "," + lampState + "," + lampBrightness + "," + lampHue;
                try {
                    encodedPayload = message.getBytes("UTF-8");
                    MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                    client.publish(topic1, mqttMessage);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MqttPersistenceException e) {
                    e.printStackTrace();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    public void increaseAllLampHue() {
        int i = 1;
        int incHue = 1400;
        if(lampState == 1 ) {

            if(lampHue <= 12600) {
                lampHue = lampHue + incHue;
            } else {
                lampHue = 14000;
            }
            while (i <= 3) {
                byte[] encodedPayload = new byte[0];
                String message = i + "," + lampState + "," + lampBrightness + "," + lampHue;
                try {
                    encodedPayload = message.getBytes("UTF-8");
                    MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                    client.publish(topic1, mqttMessage);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MqttPersistenceException e) {
                    e.printStackTrace();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    public void decreaseAllLampBrightness() {
        int i = 1;
        int decBrightness = -25;

        if(lampState == 1) {
            if(lampBrightness >= 50) {
                lampBrightness = lampBrightness + decBrightness;
            } else {
                lampBrightness = 25;
            }
            while(i <= 3) {
                String message = i + "," + lampState + "," + lampBrightness + "," + lampHue;
                try {
                    byte[] encodedPayload = new byte[0];
                    encodedPayload = message.getBytes("UTF-8");
                    MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                    client.publish(topic1, mqttMessage);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MqttPersistenceException e) {
                    e.printStackTrace();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    public void increaseAllLampBrightness() {
        int i = 1;
        int incBrightness = 25;

        if(lampState == 1) {
            if(lampBrightness <= 250) {
                lampBrightness = lampBrightness + incBrightness;
            } else {
                lampBrightness = 250;
            }
            while(i<= 3) {
                String message = i + "," + lampState + "," + lampBrightness + "," + lampHue;
                try {
                    byte[] encodedPayload = new byte[0];
                    encodedPayload = message.getBytes("UTF-8");
                    MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                    client.publish(topic1, mqttMessage);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MqttPersistenceException e) {
                    e.printStackTrace();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    public void allLampOff() {
        int i = 1;
            if(lampState == 1) {
                lampState = 0;
                while(i <= 3) {

                    String message = i + "," + lampState + "," + lampBrightness + "," + lampHue;
                    try {
                        byte[] encodedPayload = new byte[0];
                        encodedPayload = message.getBytes("UTF-8");
                        MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                        client.publish(topic1, mqttMessage);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (MqttPersistenceException e) {
                        e.printStackTrace();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }

    }

    public void allLampOn() {
        int i = 1;
        if(lampState == 0) {
            lampState = 1;
            while(i <= 3) {

                String message = i + "," + lampState + "," + lampBrightness + "," + lampHue;
                try {
                    byte[] encodedPayload = new byte[0];
                    encodedPayload = message.getBytes("UTF-8");
                    MqttMessage mqttMessage = new MqttMessage(encodedPayload);
                    client.publish(topic1, mqttMessage);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MqttPersistenceException e) {
                    e.printStackTrace();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    public void killToken() {
        try {
            token = client.disconnect();
        } catch(MqttException e) {

        }
    }
}
