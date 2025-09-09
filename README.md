# kookdonge_server

# 프로젝트 구조
## 제한 사항
- Common, Global 등의 공통 모듈은 쵀대한 만들지 않는다.
- DB entity는 하나의 패키지에만 존재한다.

## 에러처리
- 예외가 발생할때는 예외가 Code와 Message를 가져야한다.


# github convention
## PR 전략
- PR 제목: [{version}] {epic-ticket} {feature-name} ex. [v0.1.0] KDE-112 회원가입 기능
- feature 브랜치는 dev 브랜치로 PR후 머지한다.
- 배포를 해야하는 시점에 dev 브랜치를 prod 브랜치로 PR후 머지한다.
## branch 전략
- prod: 배포용 브랜치
- dev: 개발용 브랜치
- feature/{epic-ticket}/{feature-name}: 기능 개발용 브랜치
- hotfix/{epic-ticket}/{hotfix-name}: 긴급 수정용 브랜치
## hotfix 브랜치 전략
- hotfix 브랜치는 prod 브랜치에서 분기한다.
- hotfix 브랜치는 prod와 dev 브랜치로 PR후 머지한다.
