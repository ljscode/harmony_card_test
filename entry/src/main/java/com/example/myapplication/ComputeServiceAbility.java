package com.example.myapplication;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.FormBindingData;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.preferences.Preferences;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.*;
import ohos.utils.net.Uri;
import ohos.utils.zson.ZSONArray;
import ohos.utils.zson.ZSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ComputeServiceAbility extends Ability {
    // 定义日志标签
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0, "MY_TAG");
    private MyRemote remote = new MyRemote();
    private Preferences preferences;


    // FA在请求PA服务时会调用Ability.connectAbility连接PA，连接成功后，需要在onConnect返回一个remote对象，供FA向PA发送消息
    @Override
    protected IRemoteObject onConnect(Intent intent) {
        super.onConnect(intent);
        return remote.asObject();
    }

    class MyRemote extends RemoteObject implements IRemoteBroker {
        private static final int ERROR = 1;
        private static final int OPEN_SERVICE_CENTER = 1003;

        private static final int GET_COMMON_PARAMS = 1008;//获取设备信息、版本号等公共参数
        MyRemote() {
            super("MyService_MyRemote");
        }

        @Override
        public boolean onRemoteRequest(int code, MessageParcel data, MessageParcel reply, MessageOption option) {
            HiLog.info(LABEL,"onRemoteRequest===");
            Context context = getApplicationContext();

            switch (code) {
                case OPEN_SERVICE_CENTER: {
                    HiLog.info(LABEL,"onRemoteRequest===OPEN_SERVICE_CENTER");
                    String url = "abilitygallery://com.huawei.ohos.famanager/famanager?bundle=com.example.myapplication&module=entry&callerpkgname=com.example.myapplication";
                    Intent intent = new Intent();
                    Operation operationCommonComponts = new Intent.OperationBuilder()
                            .withUri(Uri.parse(url))
                            .withFlags(Intent.FLAG_ABILITY_CLEAR_MISSION)
                            .build();
                    intent.setOperation(operationCommonComponts);
                    startAbility(intent);
                    break;
                }
                default: {
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("abilityError", ERROR);
                    reply.writeString(ZSONObject.toZSONString(result));
                    return false;
                }
            }
            return true;
        }

        @Override
        public IRemoteObject asObject() {
            return this;
        }
    }
}
