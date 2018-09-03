# YAPP 13TH ROOMMATE PROJECT


## 1. 프로젝트 관리 정책

### 1) github repository fork 

원본저장소에 있는 레파지토리를 자신의 깃허브에 fork를 한다.

### 2) repository fork -> git clone

이제 포크해온 자신의 레파지토리에서 자신의 저장소 url을 복사해서 하나의 폴더안에 
```
$cd "자신이 프로젝트 하고자할 폴더"으로 이동
$git clone "자신의 레파지토리 주소"
```
### 3) 자신의 저장소에 원본저장소의 repository address 저장한다.
```
$git remote add "별칭하나 지정" "원본저장소 주소"
```
=> pork 주소가 아니라 원본저장소의 repository address 입력

### 4) remote 상태 확인
```
$git remote -v
=> 현재 remote branch 상태 확인
```

### 5) 새로운 브랜치 생성(개인 작업 공간)
```
$git checkout -b "자신의 브랜치이름" 
```
### 6) 브랜치 상태 확인
```
git branch 
```

### 7) add,commit,push 과정

```
$git add "변경된 파일명"
=>Stage에 변경된 사항을 올린다. 
전체 수정 사항 올려버리려면 
$git add .(*)

```

```
git commit -m "변경된 사항 comment적기"
=> Stage에서 Head로 올린다. 
commit 코멘트 작성
```

```
$git push origin "위에서 생성한 브랜치이름" (master로 push X)
=>Head에서 최종 repository로 올린다.
 나의 브랜치에서 수정사항을 origin으로 보낸다.
```
### 8) Pull Request(PR)
이제 여기까지 진행을 하였으면, 자신의 저장소에 들어오면  Pull Request 버튼이 활성화 되고, Pull Request 사항에 변경 Comment들을 작성한다. 

### 9) 최종 프로젝트 관리자가 Merge   할지 안할지 여부를 결정하여 Merge
merge부분에서 충돌이 나면, 프로젝트로 이동하여 충돌난 부분을 확인하여 제거 한후, 관리자는 다시 충돌없는 상태로 
add,commit,push 를 반복해준다.

### 10) Merge이후에 코드 동기화 
* 작업을 하기전에 무조건 해주어야함 중요

```
$git pull "위에서 작성한 remote 별명"
=> 코드 동기화

*예외 
혹시 git pull "위에서 작성한 remote 별명"를 하였는데,
You asked to pull from the remote 'upstream', but did not specify
a branch. Because this is not the default configured remote
for your current branch, you must specify a branch on the command line.
오류를 뱉어낸다면 branch가 제대로 지정이 되지않은것때문이다.
$git pull "remote 별명" master
명령을 사용한다 이 명령은 지정을 해주어서 마스터로 부터 값들을 변경해주는것이다. master -> remote 변경값들을 가져오는것이다.
=>변경사항 모두 동기화

```
### 11) 작업한 브랜치는 삭제(삭제 안해도 되긴하지만, 코드 충돌때문에 해주는게 좋음)
```
$git branch -d "브랜치명
```

### 최종
병합이후에 프로젝트 관리 할때에는  => 5~11번의 과정을 통하여 계속 프로젝트 관리(이때 작업하기 이전에 무조건! 10번 과정이 필요함. 아니면 코드 충돌 날 수 있음.
