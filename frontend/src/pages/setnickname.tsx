// Systems
import { useNavigate } from "react-router-dom"
import { useState } from 'react'
import axios from 'axios';
import { useAppDispatch, useAppSelector } from '../app/hooks'
import { RootState } from "../app/store";

// Other components
import tutorialBoxImg from "../assets/images/tutorial_box.png"
import decorationImg from "../assets/images/decoration.png"
import "../index.css"
import styles from "./setnickname.module.css"
import { Navbar } from '../components/navbar/navbar';
import { API_URL } from "../switchurl"

// MUI
import { Grid, Button, TextField } from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles'

// ------------------------------------------------------------------------

// 버튼 색
const theme = createTheme({
  palette: {
    primary: {
      main: '#A6D388',
    },
    success:{
      main: '#FFC3C3',
    },
    secondary:{
      main: '#6A1206',
    }
  },
});

function Setnickname() {
  const APIURL = API_URL()

  const router = useNavigate()

  const dispatch = useAppDispatch()

  // 현재 서비스 사용자아이디
  const nowUserID = useAppSelector((state : RootState)  => state.user.userId);
  let nowUserNickName = useAppSelector((state: RootState) => state.user.nickname)

  const [insertingNickName, setInsertingNickName] = useState(nowUserNickName)

  const changingNickName = (event:any) => {
    setInsertingNickName(event.target.value)
  }

  // 중복확인 함수
  const checkNickName = () => {
    axios.get(`${APIURL}api/member/confirm/`)
      .then((response) => {
        console.log(response.data)
      })
      .catch((error) => {
        console.log(error)
      })
  }
  
  // 닉네임 설정 함수
  const updateNickName = () => {
    // axios.post()
  }


  return (
    <div id="container_div">
      <Grid container id="container_div">
        {/* 왼쪽 마진 */}
        <Grid xs={0} sm={2} md={3} lg={4} xl={4.5} item id="left_div"></Grid>

        {/* 메인 콘텐츠 */}
        <Grid xs={12} sm={8} md={6} lg={4} xl={3} pt={8} item id="main_div">
          <div className={styles.navbar}>
            <Navbar/>
          </div>
          <div className={styles.description_box}>
            <img src={tutorialBoxImg} alt="" className={styles.tutorial_box}/>
            <div className={styles.description}>
              <p className={`font-bold ${styles.welcome_text} ${styles.green_text}`} style={{fontSize:'1.8rem', marginBottom:'15%'}}>사용할 닉네임을 설정해주세요.</p>

              <div style={{ width: '70%', marginBottom: '5%'}}>
                <ThemeProvider theme={theme}>
                  <TextField onChange={changingNickName} fullWidth color="secondary" label="NickName" id="fullWidth" placeholder={nowUserNickName} />
                </ThemeProvider>
              </div>

              <ThemeProvider theme={theme}>
                <Button onClick={() => checkNickName()} variant="contained" color="success" className={styles.check_button}>
                  <p className={`font-bold ${styles.check_button_text}`}>중복확인</p>
                </Button>
              </ThemeProvider>

              <ThemeProvider theme={theme}>
                <Button onClick={() => updateNickName()} variant="contained" color="primary" className={styles.setnickname_button}>
                  <p className={`${styles.green_text} ${styles.setnickname_button_text}`}>닉네임 설정하기</p>
                </Button>
              </ThemeProvider>
            </div>
          </div>
          <img src={decorationImg} alt="" className={styles.decoration}/>

        </Grid>

        {/* 오른쪽 마진 */}
        <Grid xs={0} sm={2} md={3} lg={4} xl={4.5} item id="right_div"></Grid>
      </Grid>
    </div>
  )
}

export default Setnickname