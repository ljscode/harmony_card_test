export default {
    data: {
        title: ""
    },
    onInit() {
        this.title = this.$t('strings.world');
    },
    jumpCard(){
        FeatureAbility.callAbility({
            bundleName: "com.example.myapplication",
            abilityName: "com.example.myapplication.ComputeServiceAbility",
            messageCode: 1003,
            abilityType: 0,
            data: {
            }
        });
    }
}
