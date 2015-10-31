/**
 * Firebase Datastoreへの参照
 */
var mFirebaseRef = new Firebase('https://banquetkanban.firebaseio.com/');

/**
 * Firebase Datastoreに更新があったときに呼ばれるコールバック。
 */
mFirebaseRef.child('banquetkanban').on('child_added', function(dataSnapshot) {
    var nextTask = dataSnapshot.val();

    $('#ScheduledTime').value() = nextTask.scheduledTime;
    $('#ScheduleDesc').value() = nextTask.scheduleDesc;
});