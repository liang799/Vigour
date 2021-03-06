# Vigour
[![issues](https://img.shields.io/github/issues/liang799/Vigour)](https://github.com/liang799/Vigour/issues/new)
[![forks](https://img.shields.io/github/forks/liang799/Vigour)](https://github.com/liang799/Vigour/fork)
[![stars](https://img.shields.io/github/stars/liang799/Vigour)](https://github.com/liang799/Vigour/stargazers)
![license](https://img.shields.io/github/license/liang799/Vigour)

Group Project for 2022 Mobile Application Development. 
* Our app rewards user with crypto after walking for a certain amount of steps. 
* We will be using the Etherum test network
* [Here](https://xd.adobe.com/view/1b3068a5-e074-4485-9b69-1abaa5e79f71-4e63/) is our prototype designed using Adobe XD.

## Pre-requsites
* git
* Android studio

## Getting started
1. Clone the repository using the following command
```bash
git clone https://github.com/liang799/Vigour.git
```
2. Open the project in Android studio
3. Go to `local.properties` and type in your api key. It should look something like this:
```config
sdk.dir=/path/to/sdk/
MAPS_API_KEY=___(Enter api key)_____
```
4. Build the project using Gradle

## Roles
| Raja (Certified Android guy) | Tian Pok (Jack of all Trades) | Fazith (Crypto Guy) |
| ---- | ------- | ---- |
| Splash screen (frontend) | Home Page | Google Map View |
| Wallet login (backend) | Botton Nav Bar | web3j |
| Nav Drawer (frontend) | Steps Page | Steps Details (frontend) |
| health tips (backend) | Steps History | wallet creation (backend) |

## Rules
### Branch
* We will be using topic branches:
  * Never modify `main` branch directly
  * For every new feature, add a new branch: `name/feature`
  * After you have finish implementing the feature, merge it
  * Delete the `name/feature` branch
 * Only modify your branch
 * Use `git pull --rebase` instead of `git pull`
 * Try to `rebase` instead of `merge`
* Commit often. Do not change/add over 80 files and commit.

## TODO
### Team
- [ ] Learn Remote, Local and Tracking Branches
- [ ] Learn how to resolve conflict when merging
- [x] Create Fragments
- [ ] Learn Safe args, Navigation graph
### Tian Pok
- [x] Get comfortable with Git
- [x] Create first branch
- [x] Completed all the UI
- [x] Implemented JSON parsing on Did you know section
- [x] Start working on pedometer
- [x] Test out pedometer on Boyan's old phone
- [x] Store steps in SQL database
- [x] Change Pedometer service to be a Worker
- [x] Work on RecyclerView
- [x] Pull data from SQL database from Home Frag
- [x] Pull data from SQL database from Steps Frag
### Fazith
- [x] Get comfortable with Git
- [x] Create first branch
- [x] Start working on Steps Details UI
- [x] Start learning crypto
- [ ] Work on Google login intergration with Etherum wallet
- [ ] Declare API key in local.properties
- [ ] Transactions
- [x] map
### Raja
- [x] Get comfortable with Git
- [x] Create first branch
- [x] Start working on splash screen
- [x] Add adapters
- [x] JSON Parsing on Health tips
- [x] Get remote image for Health tips
- [x] QR code
- [x] Webview
- [ ] Transactions
- [ ] Dialog fragment
